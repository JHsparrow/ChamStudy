package ChamStudy.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.AdminClassDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Service.ClassInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/adminClass")
public class AdminClassController {
	
	private final ClassInfoService classInfoService;

	@GetMapping(value = "/classList") //강의 리스트 페이지
	public String classList(Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		//List<ClassInfo> classInfo = classInfoService.getAllClass();
		Page<ClassInfo> classInfo = classInfoService.getAllClassPage(pageable);
		model.addAttribute("classInfo", classInfo);
		return "/AdminForm/AdminOnClass/classList";
	}
	
	@PostMapping(value = "/classList")
	public String classList(AdminClassDto adminClassDto, Model model) {
		List<ClassInfo> classInfo = classInfoService.getSearch(adminClassDto);
		model.addAttribute("classInfo", classInfo);
		return "/AdminForm/AdminOnClass/classList";
	}
	
	
	@GetMapping(value = "/classNew") //강의 등록 페이지
	public String classNew(Model model) {
//		List<ContentInfo> contentInfo = classInfoService.getAllContents();
//		model.addAttribute("contentInfo", contentInfo);
		model.addAttribute("adminClassDto", new AdminClassDto());
		return "/AdminForm/AdminOnClass/classNew";
	}
	
	@PostMapping(value = "/classNew") //강의 정보 등록
	public String classNew(@Valid AdminClassDto adminClassDto, BindingResult bindingResult, Model model) {
		
		MessageDto message;
		if(bindingResult.hasErrors()) {
			return "/AdminForm/AdminOnClass/classNew";
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
	public String classUpdate(AdminClassDto adminClassDto, Model model) {
		

		StringBuffer sb = new StringBuffer();
    	sb.append("\n\n");
    	sb.append("\t\t").append("Parameter (id) : ").append(adminClassDto.getId()).append("\n");
		System.out.println(sb);
		
		MessageDto message;
		
		try {
			AdminClassDto adminClass = classInfoService.getId(adminClassDto.getId());
			model.addAttribute("adminClassDto", adminClass);
		} catch(EntityNotFoundException e) {
			message = new MessageDto("존재하지 않는 강의 입니다.", "/adminClass/classList");
			model.addAttribute("adminClassDto", new AdminClassDto());
			return showMessageAndRedirect(message, model);
		}
		
		return "/AdminForm/AdminOnClass/classUpdate";
	}
	
    @PostMapping(value ="/classUpdate")
    public String itemUpdate(@Valid AdminClassDto adminClassDto, BindingResult bindingResult, Model model){
       
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
		
    	StringBuffer sb = new StringBuffer();
    	sb.append("\n\n");
    	sb.append("\t\t").append("Parameter (id) : ").append(Arrays.asList(classIdArray)).append("\n");
    	sb.append("\t\t").append("Parameter (delIndex) : ").append(delIndex).append("\n");
		System.out.println(sb);
		
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
