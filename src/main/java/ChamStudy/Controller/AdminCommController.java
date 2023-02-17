package ChamStudy.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Service.AdminCommService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/adminForm") 
public class AdminCommController { //관리자 커뮤니티 게시판
	private final AdminCommService adminCommService; 
	
	@GetMapping(value = "/comm") //관리자 커뮤니티 게시판 메인겸 자유게시판 관리
	public String commMain(Model model) {
		
		//서비스에 작성한 게시판 불러오는 메소드를 실행
		List<AdminMainCommDto> adminMainCommDtoList = adminCommService.getAdminComm();
		//view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("Comms", adminMainCommDtoList);
		
		return "AdminForm/comm/comm-main";
	}

	@GetMapping(value = "/mento")
	public String commMento() {
		return "AdminForm/comm/comm-mento";
	}
	
	@GetMapping(value = "/qna")
	public String commQna() {
		return "AdminForm/comm/comm-qna";
	}
	
	
	
}
