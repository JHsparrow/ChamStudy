package ChamStudy.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ChamStudy.Dto.CsQnaDto;
import ChamStudy.Dto.MainInformInterface;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Service.CsImgService;
import ChamStudy.Service.CsService;
import ChamStudy.Service.CsUserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/cs")
@RequiredArgsConstructor
public class CsController {
	
	private final CsService csService;
	private final CsUserService csUserService;
	private final CsImgService csImgService;
	
	private MessageDto message;
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
	//=========================================== 고객센터 메인 ===========================================
	
	@GetMapping(value="/index")
	public String csMain(Model model) {
		List<MainInformInterface> mainInformList = csService.getMainInformList();
		model.addAttribute("mainInformList", mainInformList);
		
		return "cs/index";
	}
	
	//=========================================== 공지사항 ===========================================
	
	@GetMapping(value="/inform")
	public String informMain(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Optional<Integer> page, Model model) {
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
		
		return "cs/inform";
	}
	
	@GetMapping(value="/inform/{informId}")
	public String informDetail(@PathVariable("informId") Long informId, Model model) {
		
		try {
			CsInformDto csInformDto = csService.getInform(informId);
			List<CsInformFileDto> csInformFileDtoList = csInformDto.getCsInformFileDtoList();
			
			csInformDto = csService.countingView(csInformDto);
			
			model.addAttribute("csInformDto", csInformDto);
			model.addAttribute("csInformFileList",csInformFileDtoList);
			
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/adminCs/adminInform");
			return showMessageAndRedirect(message, model);
		}
		
		return "cs/informDtl";
	}
	
	//=========================================== 자주묻는질문  ===========================================

	//자주묻는질문 리스트 (카테고리 첫 화면)
	@GetMapping(value = "/faq")
	public String csFaq(UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 30); 	//페이지 인덱스 번호는 계속 바뀌어야 하므로 삼항연산자로 처리
		Page<CsFaqListDto> faqList = csService.getFaqList(userSearchDto, csFaqListDto, pageable);
		model.addAttribute("faqList", faqList);
		model.addAttribute("maxPage", 5);
		model.addAttribute("sValue",userSearchDto.getSearchCategory());
		return "cs/faq";
	}
	
	//자주묻는질문 게시글 상세 보기
	@GetMapping(value="/faq/{faqId}")
	public String faqDetail(@PathVariable("faqId") Long faqId, Model model) { 
		try {
			CsFaqDto csFaqDto = csService.getFaq(faqId);
			model.addAttribute("csFaqDto", csFaqDto);
		} catch (Exception e) {
			message = new MessageDto("게시글을 불러오기를 실패하였습니다.", "/adminCs/adminFaq");
			return showMessageAndRedirect(message, model);
		}
		return "cs/faqDtl";
	 }
	
	// ==================================== 1:1 문의 ====================================
	@GetMapping(value="/qna")
	public String qnaMain(Model model) {
		model.addAttribute("csQnaDto", new CsQnaDto());
		model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
		return "cs/qna";
	}
	
	@PostMapping(value="submitQna")
	public String submitQna(@Valid CsQnaDto csQnaDto, BindingResult bindingResult,
			Model model, @RequestParam("csQnaImg") List<MultipartFile> qnaImgList) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(bindingResult.hasErrors()) {
			return "cs/qna";
		}
		
		try {
			csUserService.saveQna(csQnaDto, qnaImgList, email);
			csUserService.updateConId();
			message = new MessageDto("문의사항이 등록되었습니다.", "/cs/qna");
		
		} catch (Exception e) {
			message = new MessageDto("문의사항이 등록되지 않았습니다.", "/cs/qna");
		}
		return showMessageAndRedirect(message, model);
	}
}
