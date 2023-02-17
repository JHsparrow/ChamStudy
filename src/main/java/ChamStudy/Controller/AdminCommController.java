package ChamStudy.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Service.AdminCommService;


@RequestMapping(value="/adminForm")
@Controller
public class AdminCommController {
	
	private final AdminCommService adminCommService;
	
	@GetMapping(value = "/comm")
	public String commMain(Model model) {
		
		List<AdminMainCommDto> adminMainCommDtoList = adminCommService.getMainComm();
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
