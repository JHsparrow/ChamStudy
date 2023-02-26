package ChamStudy.Controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Service.PrincipalDetails;
import ChamStudy.Service.UserService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value="/users")
@Controller
@RequiredArgsConstructor
public class AdminUserController {
	
	@GetMapping("/test/signIn")
	//Authentication = di(의존성 주입)
	public @ResponseBody String loginTest(Authentication Authentication,
			@AuthenticationPrincipal UserDetails userDetails) {
		System.out.println("/test/signIn");
		System.out.println("/Authentication:" + Authentication.getPrincipal());
		return "세선정보 확인하기";
	}
	
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	@GetMapping(value = "/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("PrincipalDetails: " + principalDetails.getUsername());
		return "users/user-sign-in";
	}

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
	
	//회원 리스트에 페이지 보여주기
	@GetMapping(value = "userList")
	public String userList(UserSearchDto userSearchDto, UserInfoDto userInfoDto, Optional<Integer> page, Model model) {
		//page.isPresent() ? page.get() : 0 => url경로에 페이지 넘버가 있으면 그걸 출력, 아니면 0
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		Page<UserListDto> users = userService.getUserPage(userSearchDto,userInfoDto, pageable);
		
		model.addAttribute("users", users);	//items는 page 객체임
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "users/user-list"; 
	}
	
	//회원 상세정보 보여주기
	@GetMapping(value = "userDetail/{id}")
	public String userDetail (@PathVariable("id") Long Id, Model model) {
		
		return null;
	}
	
	//회원탈퇴
	@GetMapping(value = "delete/{id}")
	public String userDelete(@PathVariable("id")Long id, Model model, Principal principal) {
		userService.deleteUser(id);
		return "redirect:/users/userList";
	}
	
	
}
