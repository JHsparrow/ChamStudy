package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/mainForm")
@Controller
public class UserMainFormController {
	
	@GetMapping(value = "/about")
	public String about() {
		return "MainForm/about";
	}
	
	@GetMapping(value = "/courses")
	public String courses() {
		return "MainForm/courses";
	}
	
	@GetMapping(value = "/team")
	public String team() {
		return "MainForm/team";
	}
	
	@GetMapping(value = "/404")
	public String error() {
		return "MainForm/404";
	}
	
	@GetMapping(value = "/contact")
	public String contact() {
		return "MainForm/contact";
	}
	
	@GetMapping(value = "/testimonial")
	public String testimonial() {
		return "MainForm/testimonial";
	}
}
