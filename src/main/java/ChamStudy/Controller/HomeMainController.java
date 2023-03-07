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
		System.out.println(mainFormDtoList.get(0).getclassname());
		System.out.println(mainFormDtoList.get(0).getsubname());
		System.out.println(mainFormDtoList.get(0).getid());
		System.out.println(mainFormDtoList.get(0).getprice());
		System.out.println(mainFormDtoList.get(0).getstarpoint());
		System.out.println(mainFormDtoList.get(0).getimgurl());
		
		model.addAttribute("mainFormDtoList",mainFormDtoList);
		model.addAttribute("countMember",adminMainService.countMember()); //회원 수
		return "main";
	}
}
