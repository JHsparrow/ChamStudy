package ChamStudy.Controller;

import java.security.Principal;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.CompletionContentInterface;
import ChamStudy.Dto.CertificateDto;
import ChamStudy.Dto.CertificateInterface;
import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.Completion;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Service.ContentVideoService;
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
	private final ContentVideoService videoService;

	
	//마이페이지 화면 보여주기
	@GetMapping(value = "/main")
	public String signIn(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		try {
			UserInfoDto user = userMainMyPageService.getUser(email);
			model.addAttribute("user", user);
		} catch (Exception e) {
			System.out.println("addUser 메소드 내 catch문 오류");
			return "/";
		}
		
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
	
	//마이페이지 - 나의 강의실 -학습중 페이지
	@GetMapping(value="/myclass")
	public String myClass(Model model, Optional<Integer> page, MyClassLearningDto myClassLearningDto,MyClassLearningSearchDto classLearningSearchDto,Principal principal){
		String email = principal.getName();
		
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 4);
		Page<MyClassLearningDto> classLearningDtoList = userMainMyPageService.getLearningPage(myClassLearningDto,pageable,classLearningSearchDto,email);
		model.addAttribute("classSearchDto",classLearningSearchDto);
		model.addAttribute("class",classLearningDtoList);
		model.addAttribute("maxPage",5);
		return "mypage/my-page-class";
	}
	
	//마이페이지 - 나의 강의실 - 완강 페이지 
	@GetMapping(value="/completion")
	public String getCompletionList(UserSearchDto userSearchDto, CompletionListDto completionListDto, Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userMainMyPageService.getUserId(email);
		Page<CompletionListDto> completionList = userMainMyPageService.getCompletionList(userSearchDto, completionListDto, pageable, userId);
		
		model.addAttribute("email",email);
		model.addAttribute("completionList", completionList);
		model.addAttribute("maxPage", 5);
		
		return "mypage/my-page-class-completion";
	}
	
	//완강 페이지 - 재생 버튼 클릭
	@GetMapping(value="/completion/play/{contentId}")
	public String playContent(@PathVariable("contentId") Long contentId, Model model) {
		
		CompletionContentInterface completionContent = userMainMyPageService.getVideoOne(contentId);
		List<CompletionContentInterface> completionContentList = userMainMyPageService.getVideo(contentId);
		model.addAttribute("completionContent", completionContent);
		model.addAttribute("completionContentList",completionContentList);
		
		return "mypage/my-page-class-completion-play";
	}
	
	//완강 플레이리스트에서 다른 회차 강의 클릭
	@GetMapping(value="/completion/play/{contentId}/{videoId}")
	public String otherContent(@PathVariable("contentId") Long contentId, @PathVariable("videoId") Long videoId, Model model) {
		CompletionContentInterface completionContent = userMainMyPageService.getVideoOther(contentId, videoId);
		List<CompletionContentInterface> completionContentList = userMainMyPageService.getVideo(contentId);
		model.addAttribute("completionContent", completionContent);
		model.addAttribute("completionContentList",completionContentList);
		
		return "mypage/my-page-class-completion-play";
	}

	//학습중 페이지 - 강의보기 클릭
	@GetMapping(value = "/learning/watch/{contentId}/{classId}")
	public String Learning(@PathVariable("contentId") Long contentId, @PathVariable("classId") Long classId, Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		userMainMyPageService.createStudyHistory(contentId,email,"F",contentId,classId); //학습 이력 삽입(study_history)
		userMainMyPageService.createStudyResult(contentId,email,classId); //학습 이력 삽입(study_result)
		
		Long videoId = userMainMyPageService.getVideoId(contentId);
		
		CompletionContentInterface completionContent = userMainMyPageService.getLearningVideo1(classId);
		List<CompletionContentInterface> completionContentList = userMainMyPageService.getLearningVideo(contentId, classId);
		Long firstVideo = userMainMyPageService.getfirstVideoId(contentId);
		Long LastVideo = userMainMyPageService.getLastVideoId(contentId);
		model.addAttribute("completionContent", completionContent);
		model.addAttribute("completionContentList",completionContentList);
		model.addAttribute("videoId",videoId);
		model.addAttribute("firstVideoId",firstVideo);
		model.addAttribute("lastVideoId",LastVideo);
		return "mypage/Learning-Lecture";
	}
	
	//학습중 플레이리스트에서 다른 회차 강의 클릭
	@GetMapping(value="/learning/watch/{contentId}/{classId}/{videoId}")
	public String LearningContent(@PathVariable("contentId") Long contentId, @PathVariable("videoId") Long videoId, @PathVariable("classId") Long classId,Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		userMainMyPageService.createStudyHistory(contentId,email,"N",videoId,classId); //학습 이력 삽입(study_history)
		userMainMyPageService.createStudyResult(contentId,email,classId); //학습 이력 삽입(study_result)
		CompletionContentInterface completionContent = userMainMyPageService.getLearningVideoOther(email, videoId, classId);
		List<CompletionContentInterface> completionContentList = userMainMyPageService.getLearningVideo(contentId, classId);
		Long firstVideo = userMainMyPageService.getfirstVideoId(contentId);
		Long LastVideo = userMainMyPageService.getLastVideoId(contentId);
		model.addAttribute("completionContent", completionContent);
		model.addAttribute("completionContentList",completionContentList);
		model.addAttribute("videoId",videoId);
		model.addAttribute("firstVideoId",firstVideo);
		model.addAttribute("lastVideoId",LastVideo);
		
		return "mypage/Learning-Lecture";
	}
	
	//수료증 리스트 페이지
	@GetMapping(value="/certificate")
	public String Certificate (Optional<Integer> page,CertificateDto certificateDto, Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		Page<CertificateDto> certificate = userMainMyPageService.getCompletionList(certificateDto, pageable, email);
		model.addAttribute("certificateList",certificate);
		model.addAttribute("maxPage",5);
		return "mypage/certificateList";
	}
	
	@GetMapping(value="/viewCert/{compId}")
	public String viewCertification(@PathVariable(value = "compId") Long compId, Model model) {
		CertificateInterface certiInfo = userMainMyPageService.getCertificateInfo(compId);
		model.addAttribute("certiInfo", certiInfo);
		return "mypage/certificateView";
	}
	

}
