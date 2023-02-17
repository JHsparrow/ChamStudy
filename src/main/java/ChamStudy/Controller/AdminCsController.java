package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/cs")
public class AdminCsController {
	
	@GetMapping(value = "/inform")
	public String csInform() {
		return "cs/inform";
	}
	
	/*
	 * @GetMapping(value="/faq")
	 * 
	 * @GetMapping(value="/qna")
	 */
}
