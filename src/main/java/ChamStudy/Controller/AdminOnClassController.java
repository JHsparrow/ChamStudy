package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.OnContentDto;
import ChamStudy.Service.OnContentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/adminOnClass")
@RequiredArgsConstructor
public class AdminOnClassController {
	
	private final OnContentService onContentService;
	
	@GetMapping(value = "/onlineList") //실시간강의 목록
	public String onlineList() {
		return "/AdminForm/AdminOnClass/onlineList";
	}
	
	@GetMapping(value = "/onlineDetail") //강의 상세페이지
	public String onlineDetail() {
		return "/AdminForm/AdminOnClass/onlineDetail";
	}
	
	@GetMapping(value = "/onlineUpdate") //강의 수정페이지
	public String onlineUpdate() {
		return "/AdminForm/AdminOnClass/onlineUpdate";
	}
	
	@GetMapping(value = "/contents") //콘텐츠 관리페이지
	public String contents() {
		return "/AdminForm/AdminOnClass/contentList";
	}
	
	@GetMapping(value = "/contentNew") //콘텐츠 등록
	public String contentForm(Model model) {
		model.addAttribute("onContentDto", new OnContentDto());
		return "/AdminForm/AdminOnClass/contentNew";
	}
	
	@PostMapping(value = "/contentNew")
	public String contentNew(@Valid OnContentDto onContentDto, BindingResult bindingResult,	Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/AdminForm/AdminOnClass/contentNew";
		}
		
		try {
			if(0 < onContentService.saveOnContent(onContentDto)) {
				model.addAttribute("errorMessage", "콘텐츠 등록이 성공적으로 등록되었습니다!");
				return "/AdminForm/AdminOnClass/contentNew";
			} else {
				model.addAttribute("errorMessage", "콘텐츠 등록이 실패했습니다!");
				return "/AdminForm/AdminOnClass/contentNew";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "콘텐츠 등록 중 에러가 발생했습니다.");
			return "/AdminForm/AdminOnClass/contentNew";
		}
	}
	
}
