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
	
}
