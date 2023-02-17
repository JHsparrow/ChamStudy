package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value="/adminForm")
@Controller
public class AdminCommController {
	
	@GetMapping(value = "/comm")
	public String comm() {
		return "AdminForm/comm/comm-main";
	}
}
