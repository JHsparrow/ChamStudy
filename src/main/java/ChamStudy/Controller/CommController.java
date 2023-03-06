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


import ChamStudy.Dto.MainCommDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.CommCommentDto;
import ChamStudy.Dto.CommDto;
import ChamStudy.Dto.CommMentoClassNameDto;
import ChamStudy.Dto.CommWriteFormDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Service.CommService;
import ChamStudy.Service.CommSearchService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class CommController { //커뮤니티 컨트롤러
	private final CommService commService;
	private final CommSearchService commSearchService;
	
	@GetMapping(value = {"/comm","comm/{page}"})
	public String commMain(Model model,CommSearchDto commSearchDto, Optional<Integer> page,MainCommDto adminMainCommDto) {
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> comms = commSearchService.getmainCommPage(commSearchDto, adminMainCommDto ,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", comms);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("maxPage",5);

		return "MainForm/community/commMain";
	}
	
	
	//자유게시판 글쓰기 페이지 불러오기
	@GetMapping(value = "/comm/create")
	public String MainWriteForm(Model model) {
		CommDto commDto = commService.getBeforeComm();
		model.addAttribute("commWriteFormDto", new CommWriteFormDto());
		model.addAttribute("comm", commDto);
		return "MainForm/community/commMentoWrite";
	}
	
	//멘토게시판 글쓰기 페이지 불러오기
	@GetMapping(value = "/comm/Mentocreate")
	public String MentoWriteForm(Model model) {
		CommDto commDto = commService.getBeforeComm();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
		model.addAttribute("commWriteFormDto", new CommWriteFormDto());
		model.addAttribute("className", classNameDto);
		model.addAttribute("comm", commDto);
		return "MainForm/community/commMentoWrite";
	}
	
	//자유게시판 글쓰기 저장
	@PostMapping(value = "/comm/create")
	public String MainBoardCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model, @RequestParam("commImgFile") List<MultipartFile> commImgFileList,Principal principal) {
		MessageDto message;
		String email = principal.getName();
		if(bindingResult.hasErrors()) {
			return "MainForm/community/commMentoWrite";
		} 
		
		try {
			commService.saveComm(commWriteFormDto, commImgFileList,email);
			message = new MessageDto("글 등록을 완료했습니다.", "/comm");
		} catch (Exception e) {
			message = new MessageDto("글 등록을 실패했습니다.", "/comm");
			return "MainForm/community/commMentoWrite";
		}
		return "redirect:/";
	}
	
	//멘토게시판 글쓰기 저장
	@PostMapping(value = "/comm/Mentocreate")
	public String MentoBoardCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
		MessageDto message;
		String email = principal.getName();
		
		if(bindingResult.hasErrors()) {
			return "MainForm/community/commMentoWrite";
		} 
		
		try {
			commService.saveMentoComm(commWriteFormDto,email);
			message = new MessageDto("글 등록을 성공했습니다.", "/comm/mento");
		} catch (Exception e) {
			message = new MessageDto("글 등록을 실패했습니다.", "/comm/mento");
			return "MainForm/community/commMentoWrite";
		}
		return showMessageAndRedirect(message, model);
	}
	
	//스토리 수정 페이지 보기
			@GetMapping(value="/story/Mentocreate/{boardId}")
			public String storyUpdate(@PathVariable("boardId") Long boardId,Model model) {
				try {
					CommWriteFormDto writeFormDto = commService.getMentoUpdate(boardId);
					model.addAttribute(writeFormDto);
				}catch(EntityNotFoundException e) {
					model.addAttribute("errorMessage","존재하지 않는 스토리입니다.");
					model.addAttribute("storyFormDto", new CommWriteFormDto());
				}
				return "MainForm/community/commMentoWrite";
			}
	
	
	//멘토게시판
	@GetMapping(value = {"/comm/mento","/comm/mento/{page}"})
	public String commMento(Model model,CommSearchDto commSearchDto,Optional<Integer> page,MainCommDto adminMainCommDto) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 8);
		Page<MainCommDto> adminMainCommDtoList = commSearchService.getMentoCommPage(commSearchDto, adminMainCommDto, pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		model.addAttribute("commSearchDto", commSearchDto);
		model.addAttribute("maxPage", 5);
		return "MainForm/community/commMento";
	}
	
	//QnA게시판
	@GetMapping(value = "/comm/qna")
	public String commQna(Model model,CommSearchDto commSearchDto,Optional<Integer> page,MainCommDto adminMainCommDto,Principal principal) {
		String email = principal.getName();
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> adminMainCommDtoList = commSearchService.getQnACommPage(commSearchDto,adminMainCommDto,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("email",email);
		model.addAttribute("maxPage",5);
		return "MainForm/community/commQna";
	}
	
	//게시판 상세 페이지
		@GetMapping(value = "/comm/dtl/{boardId}")
		public String commDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
			try {
				//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
				CommDto adminMainCommDto = commService.getAdminCommDtl(boardId);
				List<CommCommentDto> commentList = commService.getCommentList(boardId);
				List<CommCommentDto> replyList = commService.getReplyList(boardId);
				
				//담아준 Dto를 view로 보내준다
				model.addAttribute("commWriteFormDto", new CommWriteFormDto());
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
			return "MainForm/community/comm-Dtl-Form";
		}
		
		//멘토상세페이지
		@GetMapping(value = "/comm/dtl/mento/{boardId}")
		public String commMentoDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
			try {
				//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
				CommDto adminMainCommDto = commService.getAdminCommDtl(boardId);
				List<CommCommentDto> commentList = commService.getCommentList(boardId);
				List<CommCommentDto> replyList = commService.getReplyList(boardId);
				
				//담아준 Dto를 view로 보내준다
				model.addAttribute("commWriteFormDto", new CommWriteFormDto());
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
			return "MainForm/community/comm-Mento-Dtl-Form";
		}
		
		//댓글 저장
		@PostMapping(value = "/comm/dtl/{boardId}")
		public String CommentCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
			MessageDto message;
			String email = principal.getName();
			
			if(bindingResult.hasErrors()) {
				return "MainForm/community/comm-Dtl-Form";
			} 
			
			try {
				commService.saveMentoComm(commWriteFormDto,email);
				message = new MessageDto("글 등록을 성공했습니다.", "/comm");
			} catch (Exception e) {
				message = new MessageDto("글 등록을 실패했습니다.", "/comm");
				return "MainForm/community/comm-Dtl-Form";
			}
			return "redirect:/";
		}
		
		@GetMapping(value = "/comm/delete") // 게시글 삭제&댓글 삭제
		public String commDelete(Long boardId, HttpServletRequest request,Model model) throws Exception {
			MessageDto message;
			try {
				commService.commDelete(boardId);
				message = new MessageDto("글 삭제가 완료되었습니다.", "/");
			} catch (Exception e) {
				message = new MessageDto("글 삭제가 실패되었습니다.", "/");
			}
			return showMessageAndRedirect(message, model);
		}
		
		private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
		}
		
		
	

}
