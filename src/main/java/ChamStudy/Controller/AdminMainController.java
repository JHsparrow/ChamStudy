package ChamStudy.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.AdminMainService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AdminMainController {
	
	private final AdminMainService adminMainService;
	
	@GetMapping(value = "/admin")
	public String main(Model model, Authentication authentication, @AuthenticationPrincipal UserInfo userInfo) {
		model.addAttribute("active","dash"); // 사이드 바 액티브
		model.addAttribute("countVisitor",adminMainService.countVisitor()); //방문자 수
		model.addAttribute("countMember",adminMainService.countMember()); //전체 회원 수
		model.addAttribute("countClass",adminMainService.countClass()); //전체 클래스 수
		model.addAttribute("countApply",adminMainService.countApplyList()); //수강신청 수
		
		model.addAttribute("countChartMain",adminMainService.countChartMain()); // 차트 메인별 구분
		
		model.addAttribute("countChartSubIt",adminMainService.countChartSubIt()); //차트 IT별 구분
		model.addAttribute("countChartSubCe",adminMainService.countChartSubCertificate()); //차트 자격증별 구분
		model.addAttribute("countChartSubLa",adminMainService.countChartSubLanguage()); //차트 어학별 구분
		model.addAttribute("eduInfo",adminMainService.educationInfo());
		
		return "index";
	}
}

