package ChamStudy.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Service.AdminMainService;
import ChamStudy.Service.HomeMainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeMainController {
	
	private final HomeMainService homeMainService;

	@GetMapping(value = "/")
	public String main() {
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		homeMainService.addCount();
		return "main";
	}
}
