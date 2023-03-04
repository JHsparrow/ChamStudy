package ChamStudy.Controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.ApplyListService;
import ChamStudy.Service.ClassInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mainForm")
public class ClassController { //강의 페이지
	
	@Value("${classCountInPage}")
	private int classCountInPage;
	
	private final ClassInfoService classInfoService;
	private final ApplyListService applyListService;
	
	@GetMapping(value="/class")
	public String classView(Optional<Integer> page, Model model) { //강의 리스트
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		Page<ClassInfoListDto> classInfoDtoList = classInfoService.getAllClassPage(pageable);
		model.addAttribute("classInfoDtoList", classInfoDtoList);
		model.addAttribute("maxPage",5);
		return "/MainForm/Class/classList";
	}
	
	@GetMapping(value="/detail/{classId}")
	public String classDetail(Model model, @PathVariable("classId") Long classId, Principal principal, Authentication authentication) { //강의상세페이지
		
		StringBuffer sb = new StringBuffer();
    	sb.append("\n\n");
    	sb.append("\t\t").append("Parameter (id) : ").append(classId).append("\n");
		System.out.println(sb);
		
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
			
			model.addAttribute("isApplyListNew", isApplyListNew);
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
			message = new MessageDto("존재하지 않는 콘텐츠 입니다. (1)", "/mainForm/class");
			return showMessageAndRedirect(message, model);
		} catch(Exception e) {
			e.printStackTrace();
			message = new MessageDto("존재하지 않는 콘텐츠 입니다. (2)", "/mainForm/class");
			return showMessageAndRedirect(message, model);
		}
		
		return "/MainForm/Class/classDetail";
		
	}
	
	@PostMapping(value="/detail")
	public @ResponseBody ResponseEntity classReview(Authentication authentication, @RequestBody Class_reviewDto class_reviewDto) {
		
		if (authentication == null) {
			return new ResponseEntity<String>("로그인 후 이용해 주세요", HttpStatus.UNAUTHORIZED );
		}
		
		UserInfo session = (UserInfo) authentication.getPrincipal();
		
		Long class_reviewId;
		
		try {
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
	
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}
	
}
