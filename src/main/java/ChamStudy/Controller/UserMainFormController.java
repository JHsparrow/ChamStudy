package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.UserInfoDto;

@RequestMapping(value="/mainForm")
@Controller
public class UserMainFormController {
	
	@GetMapping(value = "/about")
	public String about() {
		return "mainForm/about";
	}
	
	@GetMapping(value = "/courses")
	public String courses() {
		return "mainForm/courses";
	}
	
	@GetMapping(value = "/team")
	public String team() {
		return "mainForm/team";
	}
	
	@GetMapping(value = "/404")
	public String error() {
		return "mainForm/404";
	}
	
	@GetMapping(value = "/contact")
	public String contact() {
		return "mainForm/contact";
	}
	
	@GetMapping(value = "/testimonial")
	public String testimonial() {
		return "mainForm/testimonial";
	}
	
}
