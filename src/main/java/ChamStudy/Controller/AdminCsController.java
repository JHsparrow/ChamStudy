package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	/*
	 * @PostMapping(value = "/modifyInform") 
	 * public String modifyInform(@Valid CsInformDto csInformDto, BindingResult bindingResult, Model
	 * model, @RequestParam("csInformFile") List<MultipartFile> informFileList) {
	 * 
	 * if(bindingResult.hasErrors()) { return "cs/modifyInform"; }
	 * 
	 * try {
	 * 
	 * } catch (Exception e) {
	 * 
	 * } }
	 */
	
	//게시글 수정 페이지 보기
	@GetMapping(value="/modifyInform/{informId}")
	public String modifyInform(@PathVariable("informId") Long informId, Model model) {
		try {
			CsInformDto csInformDto = adminCsService.getInform(informId);
			model.addAttribute("csInformDto", csInformDto);
			
			System.out.println("dddddddddddddddddddddd" + csInformDto.getCsInformFileDtoList().get(0).getOriFileName());
		} catch (Exception e) {
			model.addAttribute("errorMessage", "게시물을 불러오는 중 에러가 발생하였습니다.");
			model.addAttribute("csInformDto", new CsInformDto());
			return "cs/modifyInform";
		}
		return "cs/modifyInform";
	}
	
	//게시글 수정 버튼 클릭
	@PostMapping(value="/updateInform/{informId}")
	public String updateInform(@Valid CsInformDto csInformDto, BindingResult bindingResult, Model model,
			@RequestParam("csInformFile") List<MultipartFile> informFileList) {
		
		if(bindingResult.hasErrors()) {
			return "cs/modifyInform";
		}
		
		try {
			adminCsService.updateInform(csInformDto, informFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "게시물 수정 중 에러가 발생하였습니다.");
			return "cs/modifyInform";
		}
		
		return "redirect:/";
	}
}
