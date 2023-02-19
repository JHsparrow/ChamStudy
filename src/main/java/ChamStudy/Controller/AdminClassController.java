package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="adminContent")
public class AdminClassController {
	
	@GetMapping(value = "/category") //카테고리 관리
	public String generation() {
		return "AdminContent/cateList";
	}
	
	
	
	
}
