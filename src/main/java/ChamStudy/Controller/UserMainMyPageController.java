package ChamStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/mypage")
@Controller
public class UserMainMyPageController {

	
	//로그인 화면 보여주기(관리자 홈페이지)
	@GetMapping(value = "/main")
	public String signIn() {
		return "users/my-page";
	}
	
}
