package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMainController {

	@GetMapping(value = "/main")
	public String main() {
		return "main";
	}
	
	@GetMapping(value = "/main/about")
	public String about() {
		return "MainForm/about";
	}
	
	@GetMapping(value = "/main/courses")
	public String courses() {
		return "MainForm/courses";
	}
	
	@GetMapping(value = "/main/team")
	public String team() {
		return "MainForm/team";
	}
	
	@GetMapping(value = "/main/404")
	public String testimonial() {
		return "MainForm/404";
	}
	
	@GetMapping(value = "/main/contact")
	public String contact() {
		return "MainForm/contact";
	}
}
