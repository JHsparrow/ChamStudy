package ChamStudy.Controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Service.ClassInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mainForm")
public class ClassController { //강의 페이지
	
	
	private final ClassInfoService classInfoService;
	
	@GetMapping(value="/class")
	public String classView(Optional<Integer> page, Model model) { //강의 리스트
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		Page<ClassInfoListDto> classInfoDto = classInfoService.getAllClassPage(pageable);
		model.addAttribute("classInfoDto", classInfoDto);
		model.addAttribute("maxPage",5);
		return "/MainForm/Class/classList";
	}
/*	
	@GetMapping(value="/detail/{classId}")
	public String classDetail(Model model, @PathVariable("classId")Long classId, ClassInfoListDto classInfoDto) throws Exception { //강의상세페이지
		
		MessageDto message;
		try {
			ClassInfoListDto classDetail = classInfoService.getId(classId);
			model.addAttribute("classDetail", classDetail);
			
		} catch(EntityNotFoundException e) {
			message = new MessageDto("존재하지 않는 콘텐츠 입니다.", "/mainForm/class");
			
		}
		
		return showMessageAndRedirect(message, model);
	}
	*/
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}
	
}
