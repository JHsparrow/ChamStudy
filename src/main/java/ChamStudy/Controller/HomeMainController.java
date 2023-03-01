package ChamStudy.Controller;

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
		
		homeMainService.addCount();
		return "main";
	}
}
