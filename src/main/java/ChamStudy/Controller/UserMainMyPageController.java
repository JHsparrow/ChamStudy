package ChamStudy.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Service.UserMainMyPageService;
import ChamStudy.Service.UserService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value="/mypage")
@Controller
@RequiredArgsConstructor
public class UserMainMyPageController {

	private final UserMainMyPageService userMainMyPageService;
	
	//로그인 화면 보여주기(관리자 홈페이지)
	@GetMapping(value = "/main")
	public String signIn() {
		return "mypage/my-page";
	}
	
	
	//로그인 화면 보여주기(관리자 홈페이지)
	@GetMapping(value = "/update")
	public String signIns(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(email);
		
		try {
			UserInfoDto user = userMainMyPageService.getUser(email);
			model.addAttribute("userInfoDtoUser", user);
			System.out.println(user.getEmail());
			System.out.println(user.getName());
			System.out.println(user.getPhone());
		} catch (Exception e) {
			System.out.println("addUser 메소드 내 catch문 오류");
			return "/";
		}
		
		model.addAttribute("userInfoDto", new UserInfoDto());
		
		return "mypage/my-page-update";
	}
	
	@GetMapping(value = "/learning/watch")
	public String learningLecture() {
		return "MainForm/community/Learning-Lecture";
	}
	
}
