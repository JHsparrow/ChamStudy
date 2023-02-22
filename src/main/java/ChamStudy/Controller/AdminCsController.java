package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsInformDto;
import ChamStudy.Service.AdminCsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/cs")
@RequiredArgsConstructor
public class AdminCsController {
	
	private final AdminCsService adminCsService;
	
	@GetMapping(value = "/inform")
	public String csInform() {
		return "cs/inform";
	}
	
	@GetMapping(value = "/createInform")
	public String createInform(Model model) {
		model.addAttribute("csInformDto", new CsInformDto());
		return "cs/createInform";
	}
	
	@PostMapping(value = "/uploadInform")
	public String uploadInform(@Valid CsInformDto csInformDto, BindingResult bindingResult, 
			Model model, @RequestParam("csInformFile") List<MultipartFile> informFileList) {
		
		if(bindingResult.hasErrors()) {
			return "cs/createInform";
		}
		
		try {
			adminCsService.saveInform(csInformDto, informFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생했습니다.");
			return "cs/createInform";
		}
		return "redirect:/";
	}
}
