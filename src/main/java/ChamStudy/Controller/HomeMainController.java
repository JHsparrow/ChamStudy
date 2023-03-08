package ChamStudy.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Dto.CompletionContentInterface;
import ChamStudy.Dto.MainFormDto;
import ChamStudy.Dto.MainReviewDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.AdminMainService;
import ChamStudy.Service.HomeMainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeMainController {
	
	private final HomeMainService homeMainService;
	private final AdminMainService adminMainService;

	@GetMapping(value = "/")
	public String main(Model model, Authentication authentication, @AuthenticationPrincipal UserInfo userInfo) {
		homeMainService.addCount();
		
		List<MainFormDto> mainFormDtoList = homeMainService.getMainClass();
		List<MainFormDto> mainFormDtostarList = homeMainService.getMainstarClass();
		List<MainReviewDto> mainReviewDto = homeMainService.getMainReviewDto();
		
		model.addAttribute("mainFormDtoList",mainFormDtoList); //신규 강의 리스트
		model.addAttribute("mainFormDtoStarList", mainFormDtostarList); //별점 순 정렬
		model.addAttribute("mainReviewDtoList", mainReviewDto); //리뷰 뿌려주기
		model.addAttribute("countMember",adminMainService.countMember()); //회원 수
		return "main";
	}
}
