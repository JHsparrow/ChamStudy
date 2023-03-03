package ChamStudy.Controller;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Service.UserMainMyPageService;
import ChamStudy.Service.UserService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value="/mypage")
@Controller
@RequiredArgsConstructor
public class UserMainMyPageController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserMainMyPageService userMainMyPageService;
	
	//마이페이지 화면 보여주기
	@GetMapping(value = "/main")
	public String signIn() {
		return "mypage/my-page";
	}
	
	//수정하기 화면 보여주기
	@GetMapping(value = "/update")
	public String signIns(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(email);
		
		try {
			UserInfoDto user = userMainMyPageService.getUser(email);
			model.addAttribute("userInfoDto", user);
		} catch (Exception e) {
			System.out.println("addUser 메소드 내 catch문 오류");
			return "/";
		}
		
//		model.addAttribute("userInfoDto", new UserInfoDto());
		
		return "mypage/my-page-update";
	}
	
	//수정하기 버튼 눌렀을때
	@PostMapping(value = "update") 
	public String updateUser(@Valid UserInfoDto userInfoDto,BindingResult bindingResult,
			Model model) {
	//컨트롤러에서 엔티티에 업데이트를 실행
	if(bindingResult.hasErrors()) {
		return "mypage/my-page-update";
	}
	
	try {
		userService.updateUserMypage(userInfoDto);
	}catch (Exception e) {
		model.addAttribute("errorMessage", "수정 중 에러 발생");
		return "mypage/my-page-update";
	}
		return "mypage/my-page";
}
	
	
	//========================================== 나의 강의실 ==========================================
	
	@GetMapping(value="/myclass")
	public String myClass(Model model){
		return "mypage/my-page-class";
	}
<<<<<<< HEAD

=======
	
>>>>>>> bc0bfb56ca532216a835d01d4e5afd762579103b
	@GetMapping(value = "/learning/watch")
	public String learningLecture() {
		return "MainForm/community/Learning-Lecture";
	}
	

}
