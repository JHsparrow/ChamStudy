package ChamStudy.Controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Service.AdminCommService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/adminForm")
public class AdminCommController { // 관리자 커뮤니티 게시판
	private final AdminCommService adminCommService;

	@GetMapping(value = "/comm") // 관리자 커뮤니티 게시판 메인겸 자유게시판 관리
	public String commMain(Model model) {

		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		List<AdminMainCommDto> adminMainCommDtoList = adminCommService.getAdminComm();
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);

		return "AdminForm/AdminComm/comm-main";
	}

	@GetMapping(value = "/comm/mento")
	public String commMento(Model model) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		List<AdminMainCommDto> adminMainCommDtoList = adminCommService.getAdminCommMento();
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		return "AdminForm/AdminComm/comm-mento";
	}

	@GetMapping(value = "/comm/qna")
	public String commQna(Model model) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		List<AdminMainCommDto> adminMainCommDtoList = adminCommService.getAdminCommQna();
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		return "AdminForm/AdminComm/comm-qna";
	}
	
	//게시판 상세 페이지
	@GetMapping(value = "/comm/dtl/{boardId}")
	public String commDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
		try {
			AdminMainCommDto adminMainCommDto = adminCommService.getAdminCommDtl(boardId);
			model.addAttribute("comm",adminMainCommDto);
		}catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage","존재하지 않는 게시물입니다.");
		}
		String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			model.addAttribute("referer",referer);
		return "AdminForm/AdminComm/comm-Dtl-Form";
	}

	@GetMapping(value = "/comm/delete") // 게시글 삭제
	public String commDelete(Long boardId, HttpServletRequest request) throws Exception {
		adminCommService.commDelete(boardId);
		// 게시글 삭제 후 삭제 버튼 누른 페이지로 이동하기 위해 추가한 메소드
		String referer = request.getHeader("Referer");
		request.getSession().setAttribute("redirectURI", referer);
		return "redirect:" + referer;
	}
	
	@RequestMapping(value = "/comm/back" ,method = RequestMethod.GET)
	public void rateHandler(@RequestParam("referer") String url,HttpServletResponse httpServletResponse) throws IOException {
	     httpServletResponse.sendRedirect(url);
	}
	
	
	@GetMapping(value = "/comm/deletedtl") // 게시글 상세 페이지에서 삭제 
	public String commDelete2(Long boardId, HttpServletRequest request) throws Exception {
		adminCommService.commDelete(boardId);
		String referer = request.getHeader("Referer");
		// 게시글 삭제 후 삭제 버튼 누른 페이지로 이동하기 위해 추가한 메소드
		return "redirect:" + referer;
	}

}
