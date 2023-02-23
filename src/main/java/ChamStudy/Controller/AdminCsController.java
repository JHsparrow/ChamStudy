package ChamStudy.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Service.AdminCsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/cs")
@RequiredArgsConstructor
public class AdminCsController {
	
	private final AdminCsService adminCsService;
	
	//공지사항 리스트 (카테고리 첫 화면)
	@GetMapping(value = "/inform")
	public String csInform(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Optional<Integer> page, Model model) {
		//page.isPresent() ? page.get() : 0 => url경로에 페이지 넘버가 있으면 그걸 출력, 아니면 0
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		Page<CsInformListDto> informList = adminCsService.getInformList(userSearchDto, csInformListDto, pageable);
		
		model.addAttribute("informList", informList);
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
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
