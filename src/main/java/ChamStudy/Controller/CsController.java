package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsQnaDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Service.CsImgService;
import ChamStudy.Service.CsUserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/voc")
@RequiredArgsConstructor
public class CsController {
	
	private final CsUserService csUserService;
	private final CsImgService csImgService;
	
	private MessageDto message;
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
	//=========================================== 고객센터 메인 ===========================================
	
	@GetMapping(value="/index")
	public String vocMain() {
		return "voc/index";
	}
	
	
	// ==================================== 1:1 문의 ====================================
	@GetMapping(value="/qna")
	public String qnaMain(Model model) {
		model.addAttribute("csQnaDto", new CsQnaDto());
		model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
		return "voc/qna";
	}
	
	@PostMapping(value="submitQna")
	public String submitQna(@Valid CsQnaDto csQnaDto, BindingResult bindingResult,
			Model model, @RequestParam("csQnaImg") List<MultipartFile> qnaImgList) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(bindingResult.hasErrors()) {
			return "voc/qna";
		}
		
		try {
			csUserService.saveQna(csQnaDto, qnaImgList, email);
			csUserService.updateConId();
			message = new MessageDto("문의사항이 등록되었습니다.", "/voc/qna");
		
		} catch (Exception e) {
			message = new MessageDto("문의사항이 등록되지 않았습니다.", "/voc/qna");
		}
		return showMessageAndRedirect(message, model);
	}
}
