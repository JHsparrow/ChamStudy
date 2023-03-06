package ChamStudy.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.ContentDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Service.ClassInfoService;
import ChamStudy.Service.OnContentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/adminClass")
public class AdminClassController {
	
	@Value("${classCountInPage}")
	private int classCountInPage;
	
	private final ClassInfoService classInfoService;
	private final OnContentService onContentService;

	@GetMapping(value = "/classList") //강의 리스트 페이지
	public String classList(Optional<Integer> page, Model model, UserSearchDto userSearchDto) {

		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		

		model.addAttribute("active","classInfo"); // 사이드 바 액티브

		Page<ClassInfoListDto> classInfoDtoList = classInfoService.getAllClassPage(userSearchDto, pageable);
		model.addAttribute("classInfoDtoList", classInfoDtoList);
		return "/AdminForm/AdminClass/classList";
	}
	
	@PostMapping(value = "/classList")
	public String classList(ClassInfoDto adminClassDto, @RequestParam(value = "page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, classCountInPage);
		Page<ClassInfoListDto> classInfoDtoList = classInfoService.getSearch(adminClassDto, pageable);
		model.addAttribute("classInfoDtoList", classInfoDtoList);
		return "/AdminForm/AdminClass/classList";
	}
	
	
	@GetMapping(value = "/classNew") //강의 등록 페이지
	public String classNew(Model model) {
		model.addAttribute("active","classInfo"); // 사이드 바 액티브
		List<ContentInfo> contentInfo = onContentService.getAllContent();
		model.addAttribute("contentInfo",contentInfo);
		model.addAttribute("adminClassDto", new ClassInfoDto());
		return "/AdminForm/AdminClass/classNew";
	}
	
	@PostMapping(value = "/classNew") //강의 정보 등록
	public String classNew(@Valid ClassInfoDto adminClassDto, BindingResult bindingResult, Model model) {
		
		MessageDto message;
		if(bindingResult.hasErrors()) {
			return "/AdminForm/AdminClass/classNew";
		}
		try {
			classInfoService.saveClass(adminClassDto);
			message = new MessageDto("강의 등록이 완료되었습니다.", "/adminClass/classList");
		} catch (Exception e) {
			message = new MessageDto("강의 등록이 실패했습니다.", "/adminClass/classList");
		}
		return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/classUpdate") //강의정보 수정
	public String classUpdate(ClassInfoDto adminClassDto, Model model) {
		model.addAttribute("active","classInfo"); // 사이드 바 액티브
		
		MessageDto message;
		
		try {
			/*
			 * 편법으로 전체 개수 가져오는 방법
			 * 1. 컨텐츠 데이터를 1개 조회한다 getAllContnetList(null, pageable)
			 * 2. 넘겨받은 값에서 전체 건수를 가져온다 : getTotalElements()
			 * 3. 2번에서 가져온 전체 건수만큼 조회하여 가져온다
			 * 4. 3번에서 조회한 데이터를 화면에서 select list 출력 시 사용한다
			 */
			Pageable pageable = PageRequest.of(0, 1);
			
			Page<ContentDto> contentInfoList = onContentService.getAllContnetList(null, pageable);
			long maxPage = contentInfoList.getTotalElements();
			
			pageable = PageRequest.of(0, (int) maxPage); // 실제 개수만큼 가져오기 위한 페이징 설정
			contentInfoList = onContentService.getAllContnetList(null, pageable);
			
			ClassInfoDto adminClass = classInfoService.getId(adminClassDto.getId());
			model.addAttribute("contentInfoList", contentInfoList);
			model.addAttribute("adminClassDto", adminClass);
		} catch(EntityNotFoundException e) {
			message = new MessageDto("존재하지 않는 강의 입니다.", "/adminClass/classList");
			model.addAttribute("contentInfoList", new PageImpl<>(new ArrayList<ContentDto>(), PageRequest.of(0, 1), 0));
			model.addAttribute("adminClassDto", new ClassInfoDto());
			return showMessageAndRedirect(message, model);
		}
		
		return "/AdminForm/AdminClass/classUpdate";
	}
	
    @PostMapping(value ="/classUpdate")
    public String itemUpdate(@Valid ClassInfoDto adminClassDto, BindingResult bindingResult, Model model){
       
    	MessageDto message;
    	
    	if(bindingResult.hasErrors()){
    		message = new MessageDto("강의 수정이 실패했습니다.", "/adminClass/classList");
    		return showMessageAndRedirect(message, model);
        }
    	
        try {
        	classInfoService.updateClass(adminClassDto);
        	message = new MessageDto("강의 수정이 완료되었습니다.", "/adminClass/classList");
        } catch (Exception e){
        	message = new MessageDto("강의 수정이 실패했습니다.", "/adminClass/classList");
        }

        return showMessageAndRedirect(message, model);
    }
	
    @PostMapping(value = "/classDelete") //강의정보 삭제
	public String classDelete(
			@RequestParam(value="id") Long[] classIdArray,
			@RequestParam(value="delIndex") int delIndex,
			Model model) {
		
		MessageDto message;
		
		try {
			classInfoService.deleteClass(classIdArray[delIndex]);
			message = new MessageDto("강의 삭제가 완료되었습니다.", "/adminClass/classList");
		} catch(Exception e) {
			e.printStackTrace();
			message = new MessageDto("강의 삭제가 실패되었습니다.", "/adminClass/classList");
		}
		
		return showMessageAndRedirect(message, model);
	}
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
}
