package ChamStudy.Controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.UserService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value="/users")
@Controller
@RequiredArgsConstructor
public class AdminUserController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	

	//로그인 화면 보여주기
	@GetMapping(value = "/signIn")
	public String signIn() {
		return "users/user-sign-in";
	}
	
	//회원가입 화면 보여주기
	@GetMapping(value = "/signUp")
	public String signUp(Model model) {
		model.addAttribute("userInfoDto", new UserInfoDto());
		return "users/user-sign-up";
	}
	
	//회원가입 버튼 눌렀을때 작동
	@PostMapping(value = "/new")
	public String addUser(@Valid UserInfoDto userInfoDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("addUser 메소드 내 if문 오류");
			return "users/user-sign-up";
		}
		
		try {
			UserInfo user = UserInfo.createUser(userInfoDto, passwordEncoder);
			userService.saveUser(user);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println("addUser 메소드 내 catch문 오류");
			return "users/user-sign-up";
		}
		
		return "redirect:/";
	}
	
	//회원가입 오류 날때
	@GetMapping(value = "/signIn/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "users/user-sign-in";
	}
	
	
	
}