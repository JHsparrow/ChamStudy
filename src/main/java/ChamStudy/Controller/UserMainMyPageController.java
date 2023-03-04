package ChamStudy.Controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Service.MyClassService;
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
	private final MyClassService myClassService;
	
	//마이페이지 화면 보여주기
	@GetMapping(value = "/main")
	public String signIn() {
		return "mypage/my-page";
	}
	
	//수정하기 화면 보여주기
	@GetMapping(value = "/updatepage")
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
		
//		return "redirect://mypage/my-page-update";
		return "mypage/my-page-update";
	}
	
	//수정하기 버튼 눌렀을때
	@PostMapping(value = "update") 
	public String updateUser(@Valid UserInfoDto userInfoDto,BindingResult bindingResult,
			Model model) {
		System.out.println(userInfoDto.getEmail());
		System.out.println(userInfoDto.getGubun());
		System.out.println(userInfoDto.getName());
		System.out.println(userInfoDto.getPhone());
		System.out.println(userInfoDto.getRegTime());
		System.out.println(userInfoDto.getRole());
	//컨트롤러에서 엔티티에 업데이트를 실행
	if(bindingResult.hasErrors()) {
		System.out.println("if문 에러");
		return "mypage/my-page-update";
	}
	
	try {
		userMainMyPageService.updateUser(userInfoDto);
	}catch (Exception e) {
		model.addAttribute("errorMessage", "수정 중 에러 발생");
		System.out.println("catch문 에러");
		return "mypage/my-page-update";
		
	}
		return "mypage/my-page";
}
	
	
	//========================================== 나의 강의실 ==========================================
	
	@GetMapping(value="/myclass")
	public String myClass(Model model){
		return "mypage/my-page-class";
	}
	
	//마이페이지 - 나의 강의실 - 완강 페이지 
	@GetMapping(value="/completion")
	public String getCompletionList(Model model) {
		
		String name = userMainMyPageService.getCategoryName();
		
		return "mypage/my-page-class-completion";
	}

	@GetMapping(value = "/learning/watch")
	public String learningLecture() {
		return "MainForm/community/Learning-Lecture";
	}
	

}
