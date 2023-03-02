package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Service.AdminMainService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AdminMainController {
	
	private final AdminMainService adminMainService;
	
	@GetMapping(value = "/admin")
	public String main(Model model) {
		
//		adminMainService.addCount();
		model.addAttribute("countVisitor",adminMainService.countVisitor()); //방문자 수
		model.addAttribute("countMember",adminMainService.countMember()); //전체 회원 수
		model.addAttribute("countClass",adminMainService.countClass()); //전체 클래스 수
		return "index";
	}
}

