package ChamStudy.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.AdminMainService;
import ChamStudy.Service.HomeMainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeMainController {
	
	private final HomeMainService homeMainService;
	private final AdminMainService adminMainService;

	@GetMapping(value = "/")
	public String main(Model model, Authentication authentication, @AuthenticationPrincipal UserInfo userInfo) {
		homeMainService.addCount();
		
		model.addAttribute("countMember",adminMainService.countMember()); //회원 수
		return "main";
	}
}
