package ChamStudy.Controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.MainCommDto;
import ChamStudy.Dto.CommCommentDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Dto.CommDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Service.CommSearchService;
import ChamStudy.Service.CommService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/adminForm")
public class AdminCommController { // 관리자 커뮤니티 게시판
	private final CommService adminCommService;
	private final CommSearchService commSearchService;

	@GetMapping(value = {"/comm","comm/{page}"}) // 관리자 커뮤니티 게시판 메인겸 자유게시판 관리
	public String commMain(Model model,CommSearchDto commSearchDto, Optional<Integer> page,MainCommDto MainCommDto) {
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> comms = commSearchService.getmainCommPage(commSearchDto, MainCommDto ,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", comms);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("maxPage",5);
		model.addAttribute("active","comm"); // 사이드 바 액티브
		return "adminForm/adminComm/comm-main";
	}

	@GetMapping(value = {"/comm/mento","comm/mento/{page}"})
	public String commMento(Model model,CommSearchDto commSearchDto,Optional<Integer> page,MainCommDto adminMainCommDto) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> adminMainCommDtoList = commSearchService.getMentoCommPage(commSearchDto, adminMainCommDto, pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		model.addAttribute("commSearchDto", commSearchDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("active","comm"); // 사이드 바 액티브
		return "adminForm/adminComm/comm-mento";
	}

	@GetMapping(value = {"/comm/qna","/comm/qna/{page}"})
	public String commQna(Model model,CommSearchDto commSearchDto,Optional<Integer> page,MainCommDto adminMainCommDto) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> adminMainCommDtoList = commSearchService.getQnACommPage(commSearchDto,adminMainCommDto,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("maxPage",5);
		model.addAttribute("active","comm"); // 사이드 바 액티브
		return "adminForm/adminComm/comm-qna";
	}
	
	//게시판 상세 페이지
	@GetMapping(value = "/comm/dtl/{boardId}")
	public String commDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
		try {
			//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
			model.addAttribute("active","comm"); // 사이드 바 액티브
			CommDto adminMainCommDto = adminCommService.getAdminCommDtl(boardId);
			List<CommCommentDto> commentList = adminCommService.getCommentList(boardId);
			List<CommCommentDto> replyList = adminCommService.getReplyList(boardId);
			
			//담아준 Dto를 view로 보내준다
			model.addAttribute("comm",adminMainCommDto);
			model.addAttribute("comments",commentList);
			model.addAttribute("replys",replyList);
		}catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage","존재하지 않는 게시물입니다.");
		}
		//돌아가기 버튼을 누르면 상세 페이지 이전 페이지의 정보가 있어야해서 referer를 써준다.
		//referer : 전 페이지의 정보를 가져온다.
		String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			//전 페이지의 링크를 view에 hidden으로 남기려고 작성
			model.addAttribute("referer",referer);
		return "adminForm/adminComm/comm-Dtl-Form";
	}

	@GetMapping(value = "/comm/delete") // 게시글 삭제&댓글 삭제
	public String commDelete(Long boardId, HttpServletRequest request,Model model) throws Exception {
		MessageDto message;
		try {
			adminCommService.commDelete(boardId);
			message = new MessageDto("글 삭제가 완료되었습니다.", "/adminForm/comm");
		} catch (Exception e) {
			message = new MessageDto("글 삭제가 실패되었습니다.", "/adminForm/comm");
		}
		return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/comm/back")
	public String rateHandler(HttpServletRequest request) {
		//상세 페이지에 들어오면 전 페이지의 링크를 가져온다.
		String referer = request.getParameter("referer");
		String link = referer.replace("http://localhost/adminForm/", "");
		//전 페이지의 링크를 redirect로 줘서 돌아가기 버튼 누르면 바로 전 페이지로 이동한다.
	    return "redirect:/adminForm/" + link;
	}

	@GetMapping(value = "/comm/block") // 게시글 상세 페이지에서 차단 
	public String commBlock(@RequestParam(value = "boardId") Long boardId, HttpServletRequest request,Model model) throws Exception {
		MessageDto message;
		adminCommService.commBlock(boardId);
		try {
			adminCommService.commBlock(boardId);
			message = new MessageDto("글 차단이 완료되었습니다.", "/adminForm/comm");
		} catch (Exception e) {
			message = new MessageDto("글 차단이 실패되었습니다.", "/adminForm/comm");
		}
		return showMessageAndRedirect(message, model);
	}

	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
}