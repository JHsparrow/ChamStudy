package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="adminClass")
public class AdminClassController {
	
	@GetMapping(value = "/generation") //기수제 목록
	public String generation() {
		return "AdminClass/list";
	}
}
