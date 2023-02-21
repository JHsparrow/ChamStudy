package ChamStudy.Controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.VideoDto;
import ChamStudy.Service.ContentVideoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="adminCategory")
@RequiredArgsConstructor
public class AdminCategoryController {
	
	
	@GetMapping(value = "/main") //메인 카테고리 리스트
	public String generation() {
		return "adminCategory/mainList";
	}
	
	
}
