package ChamStudy.Controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.MessageDto;
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
	public String classDetail(Model model, @PathVariable("classId")Long classId) { //강의상세페이지
		

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
			
		} catch(EntityNotFoundException e) {	
			e.printStackTrace();
			message = new MessageDto("존재하지 않는 콘텐츠 입니다.", "/mainForm/class");
			return showMessageAndRedirect(message, model);
		}
		
		return "/MainForm/Class/classDetail";
		
	}
	
//	@PostMapping(value="/detail/applyList")
//	public @ResponseBody ResponseEntity applyList(@RequestBody ApplyListDto applyListDto,  BindingResult bindingResult,  Principal principal) {
//		
//		if(bindingResult.hasErrors()) {
//			StringBuilder sb = new StringBuilder();
//			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//			
//            for (FieldError fieldError : fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//            
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
//		}
//		
//		String email = principal.getName();
//		Long applyListId;
//		
//		try {
//			applyListId = applyService.addClass(applyListDto, email);
//		} catch(Exception e) {
//			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<Long>(applyListId, HttpStatus.OK );
//	
//	}
	
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}
	
}
