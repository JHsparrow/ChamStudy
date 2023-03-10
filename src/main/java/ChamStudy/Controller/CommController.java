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
import ChamStudy.Entity.Comm_Board;
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
	
	
	//커뮤니티 메인 페이지
	@GetMapping(value = {"/comm","comm/{page}"})
	public String commMain(Model model,CommSearchDto commSearchDto, Optional<Integer> page,MainCommDto adminMainCommDto) {
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> comms = commSearchService.getmainCommPage(commSearchDto, adminMainCommDto ,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", comms);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("maxPage",5);

		return "mainForm/community/commMain";
	}
	
	
	//자유게시판 글쓰기 페이지 불러오기
	@GetMapping(value = "/comm/create")
	public String MainWriteForm(Model model) {
		model.addAttribute("commWriteFormDto", new CommWriteFormDto());
		return "mainForm/community/commWrite";
	}
	
	//QnA게시판 글쓰기 페이지 불러오기
	@GetMapping(value = "/comm/QnAcreate")
	public String QnAWriteForm(Model model) {
		model.addAttribute("commWriteFormDto", new CommWriteFormDto());
		return "mainForm/community/commQnaWrite";
	}
	
	//멘토게시판 글쓰기 페이지 불러오기
	@GetMapping(value = "/comm/Mentocreate")
	public String MentoWriteForm(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
		model.addAttribute("commWriteFormDto", new CommWriteFormDto());
		model.addAttribute("className", classNameDto);
		return "mainForm/community/commMentoWrite";
	}
	
	//자유게시판 글쓰기 저장
	@PostMapping(value = "/comm/create")
	public String MainBoardCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
		MessageDto message;
		String email = principal.getName();
		if(bindingResult.hasErrors()) {
			return "mainForm/community/commWrite";
		} 
		
		try {
			Long getId = commService.saveComm(commWriteFormDto,email);
			Comm_Board board = commService.returnBoard(getId);
			board.setOriId(getId);
			commService.updateOri(board);
			message = new MessageDto("글 등록을 완료했습니다.", "/comm");
		} catch (Exception e) {
			message = new MessageDto("글 등록을 실패했습니다.", "/comm");
			return "mainForm/community/commWrite";
		}
		return showMessageAndRedirect(message, model);
	}
	
	//QnA 글쓰기 저장
		@PostMapping(value = "/comm/QnAcreate")
		public String QnABoardCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
			MessageDto message;
			String email = principal.getName();
			if(bindingResult.hasErrors()) {
				return "mainForm/community/commQnAWrite";
			} 
			
			try {
				commService.saveComm(commWriteFormDto,email);
				message = new MessageDto("글 등록을 완료했습니다.", "/comm/qna");
			} catch (Exception e) {
				message = new MessageDto("글 등록을 실패했습니다.", "/comm/qna");
				return "mainForm/community/commQnAWrite";
			}
			return showMessageAndRedirect(message, model);
		}
	
	//멘토게시판 글쓰기 저장
	@PostMapping(value = "/comm/mentocreate")
	public String MentoBoardCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
		MessageDto message;
		String email = principal.getName();
		
		if(bindingResult.hasErrors()) {
			return "mainForm/community/commmentoWrite";
		} 
		
		try {
			commService.saveMentoComm(commWriteFormDto,email);
			message = new MessageDto("글 등록을 성공했습니다.", "/comm/mento");
		} catch (Exception e) {
			message = new MessageDto("글 등록을 실패했습니다.", "/comm/mento");
			return "mainForm/community/commMentoWrite";
		}
		return showMessageAndRedirect(message, model);
	}
	
	//멘토 수정 페이지 보기 
			@GetMapping(value="/comm/mentocreate/{boardId}")
			public String storyUpdate(@PathVariable("boardId") Long boardId,Model model) {
				CommDto commDto = commService.getcommDto(boardId);
				String email = SecurityContextHolder.getContext().getAuthentication().getName();
				List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
				try {
					CommWriteFormDto writeFormDto = commService.getMentoUpdate(boardId);
					model.addAttribute("commWriteFormDto",writeFormDto);
					model.addAttribute("className", classNameDto);
					model.addAttribute("comm", commDto);
				}catch(EntityNotFoundException e) {
					model.addAttribute("errorMessage","존재하지 않는 스토리입니다.");
					model.addAttribute("storyFormDto", new CommWriteFormDto());
				}
				return "mainForm/community/commMentoWrite";
			}
			
			//자유게시판 수정 페이지 보기 
			@GetMapping(value="/comm/boardUpdate/{boardId}")
			public String boardUpdate(@PathVariable("boardId") Long boardId,Model model) {
				CommDto commDto = commService.getcommDto(boardId);
				String email = SecurityContextHolder.getContext().getAuthentication().getName();
				List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
				try {
					CommWriteFormDto writeFormDto = commService.getMentoUpdate(boardId);
					model.addAttribute("commWriteFormDto",writeFormDto);
					model.addAttribute("className", classNameDto);
					model.addAttribute("comm", commDto);
				}catch(EntityNotFoundException e) {
					model.addAttribute("errorMessage","존재하지 않는 스토리입니다.");
					model.addAttribute("storyFormDto", new CommWriteFormDto());
				}
				return "mainForm/community/commWrite";
			}

			//자유게시판 수정 페이지 보기 
			@GetMapping(value="/comm/qnAUpdate/{boardId}")
			public String QnAUpdateForm(@PathVariable("boardId") Long boardId,Model model) {
				CommDto commDto = commService.getcommDto(boardId);
				String email = SecurityContextHolder.getContext().getAuthentication().getName();
				List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
				try {
					CommWriteFormDto writeFormDto = commService.getMentoUpdate(boardId);
					model.addAttribute("commWriteFormDto",writeFormDto);
					model.addAttribute("className", classNameDto);
					model.addAttribute("comm", commDto);
				}catch(EntityNotFoundException e) {
					model.addAttribute("errorMessage","존재하지 않는 스토리입니다.");
					model.addAttribute("storyFormDto", new CommWriteFormDto());
				}
				return "mainForm/community/commQnAWrite";
			}
			
			//자유게시판 수정페이지 저장
			@PostMapping(value = "/comm/boardUpdate/{boardId}")
			public String boardUpdate(@PathVariable("boardId") Long boardId,@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, 
					Model model) {
				MessageDto message;
				try {
					commWriteFormDto.setId(boardId);
					commService.updateboard(commWriteFormDto);
					message = new MessageDto("글 등록을 완료했습니다.", "/comm/dtl/"+boardId);
				} catch (Exception e) {
					message = new MessageDto("글 등록을 실패했습니다.", "/comm");
					return "mainForm/community/commWrite";
				}
				return showMessageAndRedirect(message, model);
			}
			
			//QnA게시판 수정페이지 저장
			@PostMapping(value = "/comm/qnAUpdate/{boardId}")
			public String QnAUpdate(@PathVariable("boardId") Long boardId,@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, 
					Model model) {
				MessageDto message;
				try {
					commWriteFormDto.setId(boardId);
					commService.updateboard(commWriteFormDto);
					message = new MessageDto("글 등록을 완료했습니다.", "/comm/qnadtl/"+boardId);
				} catch (Exception e) {
					message = new MessageDto("글 등록을 실패했습니다.", "/comm/qna");
					return "mainForm/community/commQnAWrite";
				}
				return showMessageAndRedirect(message, model);
			}
			
			//멘토 수정페이지 저장
			@PostMapping(value = "/comm/mentocreate/{boardId}")
			public String itemUpdate(@PathVariable("boardId") Long boardId,@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, 
					Model model) {
				MessageDto message;
				try {
					commWriteFormDto.setId(boardId);
					commService.updateboard(commWriteFormDto);
					message = new MessageDto("글 등록을 완료했습니다.", "/comm/dtl/mento/"+boardId);
				} catch (Exception e) {
					message = new MessageDto("글 등록을 실패했습니다.", "/comm/dtl/mento/");
					return "mainForm/community/commMentoWrite";
				}
				return showMessageAndRedirect(message, model);
			}
	
	//멘토게시판
	@GetMapping(value = {"/comm/mento","/comm/mento/{page}"})
	public String commMento(Model model,CommSearchDto commSearchDto,Optional<Integer> page,MainCommDto adminMainCommDto) {
		// 서비스에 작성한 게시판 불러오는 메소드를 실행
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 18);
		Page<MainCommDto> adminMainCommDtoList = commSearchService.getMentoCommPage(commSearchDto, adminMainCommDto, pageable);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<CommMentoClassNameDto> classNameDto = commService.getMentoClassName(email);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", adminMainCommDtoList);
		model.addAttribute("commSearchDto", commSearchDto);
		model.addAttribute("classNameDto", classNameDto);
		model.addAttribute("maxPage", 5);
		return "mainForm/community/commMento";
	}
	
	//QnA게시판
	@GetMapping(value = {"/comm/qna","/comm/qna/{page}"})
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
		return "mainForm/community/commQna";
	}
	
	//게시판 상세 페이지
		@GetMapping(value = "/comm/dtl/{boardId}")
		public String commDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request,Principal principal) {
			String email = principal.getName();
			Long getid = boardId;
			commService.viewCount(boardId);
			try {
				//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
				CommDto adminMainCommDto = commService.getAdminCommDtl(boardId);
				List<CommCommentDto> commentList = commService.getCommentList(boardId);
				
				//담아준 Dto를 view로 보내준다
				model.addAttribute("commWriteFormDto", new CommWriteFormDto());
				model.addAttribute("comm",adminMainCommDto);
				model.addAttribute("comments",commentList);
				model.addAttribute("boardId",getid);
				model.addAttribute("email",email);
			}catch(Exception e) {
				model.addAttribute("errorMessage","존재하지 않는 게시물입니다.");
			}
			//돌아가기 버튼을 누르면 상세 페이지 이전 페이지의 정보가 있어야해서 referer를 써준다.
			//referer : 전 페이지의 정보를 가져온다.
			String referer = request.getHeader("Referer");
				request.getSession().setAttribute("redirectURI", referer);
				//전 페이지의 링크를 view에 hidden으로 남기려고 작성
				model.addAttribute("referer",referer);
			return "mainForm/community/comm-Dtl-Form";
		}
		//Qna 상세 페이지
		@GetMapping(value = "/comm/qnAdtl/{boardId}")
		public String commQnaDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request,Principal principal) {
			String email = principal.getName();
			Long getid = boardId;
			commService.viewCount(boardId);
			try {
				//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
				CommDto adminMainCommDto = commService.getAdminCommDtl(boardId);
				List<CommCommentDto> commentList = commService.getCommentList(boardId);
				
				//담아준 Dto를 view로 보내준다
				model.addAttribute("commWriteFormDto", new CommWriteFormDto());
				model.addAttribute("comm",adminMainCommDto);
				model.addAttribute("comments",commentList);
				model.addAttribute("boardId",getid);
				model.addAttribute("email",email);
			}catch(Exception e) {
				model.addAttribute("errorMessage","존재하지 않는 게시물입니다.");
			}
			//돌아가기 버튼을 누르면 상세 페이지 이전 페이지의 정보가 있어야해서 referer를 써준다.
			//referer : 전 페이지의 정보를 가져온다.
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			//전 페이지의 링크를 view에 hidden으로 남기려고 작성
			model.addAttribute("referer",referer);
			return "mainForm/community/qna-Dtl-Form";
		}
		
		//자유게시판 댓글 작성
		@PostMapping(value = "/comm/comment/create")
		public String commentCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal,HttpServletRequest request) {
			String getId = request.getParameter("boardId");
			Long boardId = Long.parseLong(getId);
			MessageDto message;
			String email = principal.getName();
			if(bindingResult.hasErrors()) {
				return "mainForm/community/comm-Dtl-Form";
			} 
			
			try {
				commService.saveComm(commWriteFormDto,email);
				message = new MessageDto("글 등록을 완료했습니다.", "/comm/dtl/"+boardId);
			} catch (Exception e) {
				message = new MessageDto("글 등록을 실패했습니다.", "/comm");
				return "mainForm/community/comm-Dtl-Form";
			}
			return showMessageAndRedirect(message, model);
		}

		//자유게시판 댓글 작성
		@PostMapping(value = "/comm/qnacomment/create")
		public String qnacommentCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal,HttpServletRequest request) {
			String getId = request.getParameter("boardId");
			Long boardId = Long.parseLong(getId);
			MessageDto message;
			String email = principal.getName();
			if(bindingResult.hasErrors()) {
				return "mainForm/community/qna-Dtl-Form";
			} 
			
			try {
				commService.saveComm(commWriteFormDto,email);
				message = new MessageDto("글 등록을 완료했습니다.", "/comm/qnAdtl/"+boardId);
			} catch (Exception e) {
				message = new MessageDto("글 등록을 실패했습니다.", "/comm/qna");
				return "mainForm/community/qna-Dtl-Form";
			}
			return showMessageAndRedirect(message, model);
		}
		
		//멘토상세페이지
		@GetMapping(value = "/comm/dtl/mento/{boardId}")
		public String commMentoDtl(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request,Principal principal) {
			String email = principal.getName();
			//조회수 증가 메소드
			commService.viewCount(boardId);
			try {
				//서비스에서 상세 페이지를 가져와주는 메소드 실행하여 Dto에 담아준다.
				CommDto adminMainCommDto = commService.getAdminCommDtl(boardId);
				List<CommCommentDto> commentList = commService.getCommentList(boardId);
				
				//담아준 Dto를 view로 보내준다
				model.addAttribute("commWriteFormDto", new CommWriteFormDto());
				model.addAttribute("comm",adminMainCommDto);
				model.addAttribute("comments",commentList);
				model.addAttribute("email",email);
			}catch(EntityNotFoundException e) {
				model.addAttribute("errorMessage","존재하지 않는 게시물입니다.");
			}
			//돌아가기 버튼을 누르면 상세 페이지 이전 페이지의 정보가 있어야해서 referer를 써준다.
			//referer : 전 페이지의 정보를 가져온다.
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			//전 페이지의 링크를 view에 hidden으로 남기려고 작성
			model.addAttribute("referer",referer);
			return "mainForm/community/comm-Mento-Dtl-Form";
		}
		
		//댓글 저장
		@PostMapping(value = "/comm/dtl/{boardId}")
		public String CommentCreate(@Valid CommWriteFormDto commWriteFormDto, BindingResult bindingResult, Model model,Principal principal) {
			MessageDto message;
			String email = principal.getName();
			
			if(bindingResult.hasErrors()) {
				return "mainForm/community/comm-Dtl-Form";
			} 
			
			try {
				commService.saveMentoComm(commWriteFormDto,email);
				message = new MessageDto("글 등록을 성공했습니다.", "/comm");
			} catch (Exception e) {
				message = new MessageDto("글 등록을 실패했습니다.", "/comm");
				return "mainForm/community/comm-Dtl-Form";
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
