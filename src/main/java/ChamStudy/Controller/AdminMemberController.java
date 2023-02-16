package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/members")
@Controller
public class AdminMemberController {

	@GetMapping(value = "/signIn")
	public String signIn() {
		return "members/member-sign-in";
	}
	
	@GetMapping(value = "/signUp")
	public String signUp() {
		return "members/member-sign-up";
	}
}
