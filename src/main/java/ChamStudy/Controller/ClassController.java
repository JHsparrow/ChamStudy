package ChamStudy.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.ClassReviewListDto;
import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.ApplyListService;
import ChamStudy.Service.ClassInfoService;
import ChamStudy.Service.Class_reviewService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mainForm")
public class ClassController { //강의 페이지
	
	@Value("${classCountInPage}")
	private int classCountInPage;
	
	private final ClassInfoService classInfoService;
	private final ApplyListService applyListService;
	private final Class_reviewService class_reviewService;
	
	@GetMapping(value="/class")
	public String classView(Optional<Integer> page, Model model, UserSearchDto userSearchDto) { //강의 리스트
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		List<SubCategory> subCateList = classInfoService.getSubCate();
		model.addAttribute("subLists",subCateList);
		Page<ClassInfoListDto> classInfoDtoList = classInfoService.getAllClassPage(userSearchDto, pageable);
		model.addAttribute("classInfoDtoList", classInfoDtoList);
		model.addAttribute("maxPage",classInfoDtoList.getTotalPages());
		model.addAttribute("userSearchDto", userSearchDto);
		return "/mainForm/class/classList";
	}
	
	@PostMapping(value="/class")
	public String classView(Optional<Integer> page, Model model, UserSearchDto userSearchDto, ClassInfoDto adminClassDto) { //강의 리스트
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<ClassInfoListDto> classInfoDtoList = classInfoService.getAllClassPage(userSearchDto, pageable);
		
		List<SubCategory> subCateList = classInfoService.getSubCate();
		model.addAttribute("subLists",subCateList);
		model.addAttribute("classInfoDtoList", classInfoDtoList);
		model.addAttribute("maxPage", classInfoDtoList.getTotalPages());
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("checkCate",userSearchDto.getSearchQuery());
		
		return "/mainForm/class/classList";
	}
	
	@GetMapping(value="/detail/{classId}")
	public String classDetail(Model model, @PathVariable("classId") Long classId, Principal principal, 
					Authentication authentication, Class_reviewDto class_reviewDto) { //강의상세페이지	
		
		MessageDto message;
		try {
			
			ClassInfoDto adminClassDto = classInfoService.getId(classId);
			model.addAttribute("adminClassDto", adminClassDto);
			
			ClassInfoListDto classDetail = classInfoService.getClassInfo(adminClassDto);
			model.addAttribute("classDetail", classDetail);

			String isApplyListNew = "Y"; //신규강의등록인지 확인. Y:신규, N:이미등록되었다
			
			//Authentication 객체가 null 이면 세션에 값이 없다 (=로그인하지 않았다)
			if (authentication == null) {
				isApplyListNew = "Y";
			} else {
				UserInfo session = (UserInfo) authentication.getPrincipal();
				
				ApplyListDto applyListDto = new ApplyListDto(null, null, null, null, null);
				applyListDto.setClassId(classId);
				
				Long applyId = applyListService.getApplyId(applyListDto, session.getEmail());
				
				if (applyId > 0) {
					isApplyListNew = "N";
				} else {
					isApplyListNew = "Y";
				}
				
			}
			
			List<ClassReviewListDto> classReviewList = class_reviewService.getClassInfoReviews(class_reviewDto);
			
			model.addAttribute("classReviewList", classReviewList);
			
			model.addAttribute("isApplyListNew", isApplyListNew);
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
			message = new MessageDto("존재하지 않는 강의 입니다. (1)", "/mainForm/class");
			return showMessageAndRedirect(message, model);
		} catch(Exception e) {
			e.printStackTrace();
			message = new MessageDto("존재하지 않는 강의 입니다. (2)", "/mainForm/class");
			return showMessageAndRedirect(message, model);
		}
		
		return "/mainForm/class/classDetail";
		
	}
	
	@PostMapping(value="/detail")
	public @ResponseBody ResponseEntity classReview(@RequestBody Class_reviewDto class_reviewDto, BindingResult bindingResult,Principal principal, Authentication authentication, Model model) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
		
		//Authentication 객체가 null 이면 세션에 값이 없다 (=로그인하지 않았다)
		//401에러
		if (authentication == null) {
			return new ResponseEntity<String>("로그인 후 이용해 주세요", HttpStatus.UNAUTHORIZED );
		}
		
		UserInfo session = (UserInfo) authentication.getPrincipal();
		
		Long reviewId = (long) -1;
		
		try {

			reviewId = class_reviewService.addReview(class_reviewDto, session.getEmail());
			
			return new ResponseEntity<Long>(reviewId, HttpStatus.OK );
				
		} catch(Exception e) {
			reviewId = (long) -9;
			e.printStackTrace();
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//리뷰 삭제
	@GetMapping(value = "/review/delete/{reviewId}/{classId}")
	public String deleteReview(@PathVariable(value="reviewId")Long reviewId, @PathVariable(value="classId")Long classId, Model model, Principal principal, Authentication authentication) {
		
		MessageDto message;
		
		//Authentication 객체가 null 이면 세션에 값이 없다 (=로그인하지 않았다)
		//401에러
		if (authentication == null) {
			message = new MessageDto("로그인 후에 삭제가 가능합니다.", "/mainForm/detail/" + classId);
			return showMessageAndRedirect(message, model);
		}
		
		UserInfo session = (UserInfo) authentication.getPrincipal();
		
		try {
			boolean isValid = class_reviewService.validateReview(reviewId, session);
			
			if(isValid) {
				class_reviewService.deleteReview(reviewId);
				
				message = new MessageDto("리뷰 삭제가 완료되었습니다.", "/mainForm/detail/" + classId);
			} else {
				message = new MessageDto("리뷰 삭제가 실패되었습니다.", "/mainForm/detail/" + classId);
			}
		} catch(Exception e) {
			e.printStackTrace();
			message = new MessageDto("리뷰 삭제가 실패되었습니다.", "/mainForm/detail/" + classId);
		}
		
		return showMessageAndRedirect(message, model);
	}
	
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}
	
}
