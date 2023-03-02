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
		
		model.addAttribute("countMainIt",adminMainService.countApplyListMainIt()); //IT 신청자 수
		model.addAttribute("countMainLi",adminMainService.countApplyListMainLi()); //자격증 신청자 수
		model.addAttribute("countMainLa",adminMainService.countApplyListMainLa()); //어학 신청자 수
		
		model.addAttribute("countJava",adminMainService.countApplyListMainItSubJ()); //IT 자바 신청자 수
		model.addAttribute("countPython",adminMainService.countApplyListMainItSubP()); //IT 파이썬 신청자 수
		model.addAttribute("countItEtc",adminMainService.countApplyListMainItSubEt()); //IT 기타 신청자 수
		
		model.addAttribute("countExcel",adminMainService.countApplyListMainLiSubE()); //자격증 엑셀 신청자 수
		model.addAttribute("countPublic",adminMainService.countApplyListMainLiSubP()); //자격증 공무원 신청자 수
		model.addAttribute("countLiEtc",adminMainService.countApplyListMainLiSubEt()); //자격증 기타 신청자 수
		
		model.addAttribute("countChinese",adminMainService.countApplyListMainLaSubC()); //어학 중국어 신청자 수
		model.addAttribute("countEnglish",adminMainService.countApplyListMainLaSubE()); //어학 영어 신청자 수
		model.addAttribute("countLaEtc",adminMainService.countApplyListMainLaSubEt()); //어학 기타 신청자 수
		model.addAttribute("eduInfo",adminMainService.educationInfo());
		
		return "index";
	}
}

