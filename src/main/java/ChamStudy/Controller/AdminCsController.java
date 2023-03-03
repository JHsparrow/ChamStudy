package ChamStudy.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsFaqDto;
import ChamStudy.Dto.CsFaqListDto;
import ChamStudy.Dto.CsInformDto;
import ChamStudy.Dto.CsInformFileDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Dto.WarnBoardDto;
import ChamStudy.Entity.CsInform;
import ChamStudy.Service.CsFileService;
import ChamStudy.Service.CsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/cs")
@RequiredArgsConstructor
public class AdminCsController {
	
	private final CsService csService;
	private final CsFileService csFileService;
	
	private MessageDto message;
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
	//=========================================== 공지사항 ===========================================
	
	//공지사항 리스트 (카테고리 첫 화면)
	@GetMapping(value = "/inform")
	public String csInform(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Optional<Integer> page, Model model) {
		//page.isPresent() ? page.get() : 0 => url경로에 페이지 넘버가 있으면 그걸 출력, 아니면 0
		model.addAttribute("active","csInform"); // 사이드 바 액티브
		int maxPage = 10;
		int fixedInform = csService.NumberOfFixed();
		
		Pageable pageable = null;
		
		if(page.isEmpty() || page.get() == 0) {
			maxPage -= fixedInform;
			pageable = PageRequest.of(page.isPresent() ? page.get() : 0, maxPage); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		} else {
			pageable = PageRequest.of(page.isPresent() ? page.get() : 0, maxPage); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		}
		
		Page<CsInformListDto> informList = csService.getInformList(userSearchDto, csInformListDto, pageable);
		Page<CsInformListDto> fixedInformList = csService.getFixedInformList(userSearchDto, csInformListDto, pageable);
		
		model.addAttribute("informList", informList);
		model.addAttribute("fixedInformList", fixedInformList);
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "cs/AdminInform";
	}
	
	//(첫 화면에서) 등록하기 버튼 클릭
	@GetMapping(value = "/informForm")
	public String createInform(Model model) {
		model.addAttribute("active","csInform"); // 사이드 바 액티브
		model.addAttribute("csInformDto", new CsInformDto());
		model.addAttribute("email",SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println("작성자 아이디: " + SecurityContextHolder.getContext().getAuthentication().getName());
		return "cs/AdminInformForm";
	}

	
	//공지사항 등록 버튼
	@PostMapping(value = "/uploadInform")
	public String uploadInform(@Valid CsInformDto csInformDto, BindingResult bindingResult, 
			Model model, @RequestParam("csInformFile") List<MultipartFile> informFileList) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(bindingResult.hasErrors()) {
			return "cs/AdminInformForm";
		}
		
		try {
			csService.saveInform(csInformDto, informFileList, email);
			message = new MessageDto("공지사항 등록이 완료되었습니다.", "/cs/inform");
			
		} catch (Exception e) {
			message = new MessageDto("공지사항 등록이 실패하였습니다.", "/cs/inform");
		}
		return showMessageAndRedirect(message, model);
	}
	
	//공지사항 게시글 상세 보기
	@GetMapping(value="/informDtl/{informId}")
	public String informDetail(@PathVariable("informId") Long informId, Model model) { 
		model.addAttribute("active","csInform"); // 사이드 바 액티브
		try {
			CsInformDto csInformDto = csService.getInform(informId);
			List<CsInformFileDto> csInformFileDtoList = csInformDto.getCsInformFileDtoList();
			model.addAttribute("csInformDto", csInformDto);
			model.addAttribute("csInformFileList",csInformFileDtoList);
			
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/cs/AdminInform");
			return showMessageAndRedirect(message, model);
		}
		
		return "cs/AdminInformDtl";
	 }
	
	//게시글 수정 페이지 보기
	@GetMapping(value="/informMdf/{informId}")
	public String modifyInform(@PathVariable("informId") Long informId, Model model) {
		model.addAttribute("active","csInform"); // 사이드 바 액티브
		try {
			CsInformDto csInformDto = csService.getInform(informId);
			model.addAttribute("csInformDto", csInformDto);
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/cs/inform");
			return showMessageAndRedirect(message, model);
		}
		return "cs/AdminInformMdf";
	}
	
	//게시글 수정 버튼 클릭
	@PostMapping(value="/updateInform/{informId}")
	public String updateInform(@Valid CsInformDto csInformDto, BindingResult bindingResult, Model model,
			@RequestParam("csInformFile") List<MultipartFile> informFileList, @PathVariable("informId")Long informId) {
		if(bindingResult.hasErrors()) {
			return "cs/AdminInformMdf";
		}
		
		try {
			csService.updateInform(csInformDto, informFileList);
			message = new MessageDto("게시글 수정이 완료되었습니다.", "/cs/informDtl/"+informId);
		} catch (Exception e) {
			message = new MessageDto("게시글 수정이 실패하였습니다.", "/cs/inform");
		}
		
		return showMessageAndRedirect(message, model);
	}
	
	//공지사항 게시글 삭제
	@GetMapping(value="/deleteInform/{informId}")
	public String deleteInform(@PathVariable("informId") Long informId, Model model) {
		
		try {
			CsInformDto csInformDto = csService.getInform(informId);
			csFileService.deleteInformFile(informId);
			csService.deleteInform(informId);
			
			message = new MessageDto("게시글 삭제가 완료되었습니다.", "/cs/inform");
		} catch (Exception e) {
			message = new MessageDto("게시글 삭제를 실패하였습니다.", "/cs/informDtl/"+informId);
		}
		return showMessageAndRedirect(message, model);
	}
	
	//=========================================== 자주 묻는 질문 ===========================================

	//자주묻는질문 리스트 (카테고리 첫 화면)
	@GetMapping(value = "/faq")
	public String csFaq(UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Optional<Integer> page, Model model) {
		model.addAttribute("active","csFaq"); // 사이드 바 액티브
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		Page<CsFaqListDto> faqList = csService.getFaqList(userSearchDto, csFaqListDto, pageable);
		model.addAttribute("faqList", faqList);
		model.addAttribute("maxPage", 5);
		model.addAttribute("sValue",userSearchDto.getSearchCategory());
		return "cs/AdminFaq";
	}
	
	//(첫 화면에서) 등록하기 버튼 클릭
	@GetMapping(value = "/createFaq")
	public String createFaq(Model model) {
		model.addAttribute("active","csFaq"); // 사이드 바 액티브
		model.addAttribute("csFaqDto", new CsFaqDto());
		model.addAttribute("email",SecurityContextHolder.getContext().getAuthentication().getName());
		return "cs/AdminFaqForm";
	}
	
	//자주묻는 질문 등록 버튼
	@PostMapping(value = "/uploadFaq")
	public String uploadInform(@Valid CsFaqDto csFaqDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "cs/AdminFaqForm";
		}
		
		try {
			csService.saveFaq(csFaqDto);
			message = new MessageDto("게시글 등록이 완료되었습니다.", "/cs/faq");
		} catch (Exception e) {
			message = new MessageDto("게시글 등록이 실패하였습니다.", "/cs/faq");
		}
		return showMessageAndRedirect(message, model);
	}

	//자주묻는질문 수정 페이지 보기
	@GetMapping(value="/faqMdf/{faqId}")
	public String modifyFaq(@PathVariable("faqId") Long faqId, Model model) {
		model.addAttribute("active","csFaq"); // 사이드 바 액티브
		try {
			CsFaqDto csFaqDto = csService.getFaq(faqId);
			model.addAttribute("csFaqDto", csFaqDto);
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/cs/faq");
			return showMessageAndRedirect(message, model);
		}
		return "cs/AdminFaqMdf";
	}
	
	//게시글 수정 버튼 클릭
	@PostMapping(value="/updateFaq/{faqId}")
	public String updateInform(@Valid CsFaqDto csFaqDto, BindingResult bindingResult, Model model,
			@PathVariable("faqId")Long faqId) {
		if(bindingResult.hasErrors()) {
			return "cs/AdminFaqMdf";
		}
		
		try {
			csService.updateFaq(csFaqDto);
			message = new MessageDto("게시글 수정이 완료되었습니다.", "/cs/faqDtl/"+faqId);
		} catch (Exception e) {
			message = new MessageDto("게시글 수정이 실패하였습니다.", "/cs/inform");
		}
		
		return showMessageAndRedirect(message, model);
	}
	
	//자주묻는질문 게시글 상세 보기
	@GetMapping(value="/faqDtl/{faqId}")
	public String faqDetail(@PathVariable("faqId") Long faqId, Model model) { 
		model.addAttribute("active","csFaq"); // 사이드 바 액티브
		try {
			CsFaqDto csFaqDto = csService.getFaq(faqId);
			model.addAttribute("csFaqDto", csFaqDto);
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/cs/AdminFaq");
			return showMessageAndRedirect(message, model);
		}
		
		return "cs/AdminFaqDtl";
	 }
	
	//자주묻는질문 게시글 삭제
	@GetMapping(value="/deleteFaq/{faqId}")
	public String deleteFaq(@PathVariable("faqId") Long faqId, Model model) {
		
		try {
			CsFaqDto csFaqDto = csService.getFaq(faqId);
			csService.deleteFaq(faqId);
			
			message = new MessageDto("게시글 삭제가 완료되었습니다.", "/cs/faq");
		} catch (Exception e) {
			message = new MessageDto("게시글 삭제를 실패하였습니다.", "/cs/faqDtl/"+faqId);
		}
		return showMessageAndRedirect(message, model);

	}
	
	//=========================================== 1:1 문의 게시판 ===========================================
	
	@GetMapping(value="/qna")
	public String csQna(Model model) {
		model.addAttribute("active","csQna"); // 사이드 바 액티브
		return "cs/AdminQna";
	}
	
	/*
	//공지사항 리스트 (카테고리 첫 화면)
	@GetMapping(value = "/inform")
	public String csInform(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Optional<Integer> page, Model model) {
		//page.isPresent() ? page.get() : 0 => url경로에 페이지 넘버가 있으면 그걸 출력, 아니면 0
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<CsInformListDto> informList = csService.getInformList(userSearchDto, csInformListDto, pageable);
		
		model.addAttribute("informList", informList);
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "cs/AdminInform";
	}
	*/
	
	@GetMapping(value="/createQna")
	public String createQna(){
		return "cs/AdminQnaForm";
	}
	
	@GetMapping(value="/qnaDtl")
	public String qnaDetail() {
		return "cs/AdminQnaDtl";
	}
	
	
	
	//=========================================== 경고 게시판 ===========================================
	
	/*
	//경고 게시판 리스트
	@GetMapping(value="/warn")
	public String warnList(UserSearchDto userSearchDto, WarnBoardDto warnBoardDto, Optional<Integer> page, Model model) {

		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		Page<WarnBoardDto> warnList = csService.getWarnList(userSearchDto, warnBoardDto, pageable);
		model.addAttribute("warnLists", warnList);
		System.out.println(warnList);
		model.addAttribute("maxPage", 5);
		return "cs/AdminWarnBoard";
	}
	*/
}
