package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import ChamStudy.Dto.CommCommentDto;
import ChamStudy.Dto.CommImgDto;
import ChamStudy.Dto.CommDto;
import ChamStudy.Dto.CommFreeBoardFormDto;
import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Repository.CommImgRepository;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional // 서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려준다.
@RequiredArgsConstructor
@Component
public class CommService { // 관리자 커뮤니티 게시판 서비스
	private final CommRepository commRepository;
	private final CommImgRepository commImgRepository;
	private final CommImgService commImgService;

	public List<CommDto> getAdminComm() { // 관리자 메인 페이지에 뿌려줄 게시판 리스트를 불러온다. // 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
	 List<Comm_Board> commList = commRepository.findF(); // 화면에 뿌려주기 위해 담을 Dto 리스트 작성 
	 List<CommDto> mainCommDtoList = new ArrayList<>();
	 
	 // 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업 // 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다.
	 
	 for (Comm_Board board : commList) { CommDto commDto = new CommDto(board);  mainCommDtoList.add(commDto); } 
	 return mainCommDtoList; }

	
	public Long saveComm(CommFreeBoardFormDto commImgDtoList, List<MultipartFile> commImgFileList) throws Exception{
		Comm_Board board = commImgDtoList.createBoard();
		commRepository.save(board);
		
		for(int i=0; i<commImgFileList.size(); i++) {
			Comm_Board_Img commImg = new Comm_Board_Img();
			commImg.setBoard(board);
			
			//첫번째 이미지 일때 대표 상품 이미지 여부 지정
			commImgService.saveCommImg(commImg, commImgFileList.get(i));
		}
		return board.getId();
	}
	
	public Long updateItem(CommFreeBoardFormDto boardFormDto, List<MultipartFile> commImgFileList) throws Exception {
		Comm_Board board = commRepository.findById(boardFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		board.updateComm(boardFormDto);
		
		List<Long> commImgIds = boardFormDto.getCommImgIds(); //상품 이미지 아이디 리스트를 조회
		
		for(int i=0; i<commImgFileList.size(); i++) {
			commImgService.updateItemImg(commImgIds.get(i), commImgFileList.get(i));
		}
		return board.getId();
	}
	
	  public CommDto getAdminCommDtl(Long boardId) { // 관리자 게시판 상세페이지에 뿌려줄 게시판 리스트를 불러온다. 
		  List<Comm_Board_Img> commImgList =
	  commImgRepository.findByBoardIdOrderByIdAsc(boardId); List<CommImgDto>
	  commImgDtoList = new ArrayList<>();
	  
	  // 엔티티 객체 -> Dto객체로 변환 
	  for (Comm_Board_Img commImg : commImgList) {
	  CommImgDto commImgDto = CommImgDto.of(commImg);
	  commImgDtoList.add(commImgDto); } 
	  // 2.item테이블에 있는 데이터를 가져온다. 
	  Comm_Board board = commRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
	  // 엔티티 객체 -> Dto객체로 변환 
	  CommDto commDto = new CommDto(board);
	  
	  // 상품의 이미지 정보를 넣어준다. commDto.setCommImgDtos(commImgDtoList);
	  
	  return commDto; }
	 

	public List<CommCommentDto> getCommentList(Long boardId) { // 관리자 게시판 상세페이지에 뿌려줄 댓글 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		Comm_Board comm_Board = commRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<Comm_Board> boardList = commRepository.findComment(comm_Board.getId());
		List<CommCommentDto> commentList = new ArrayList<>();

		for (Comm_Board comment : boardList) {
			CommCommentDto commentDto = new CommCommentDto(comment);
			commentList.add(commentDto);
		}

		return commentList;
	}

	public List<CommCommentDto> getReplyList(Long boardId) { // 관리자 게시판 상세페이지에 뿌려줄 댓글 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		Comm_Board comm_Board = commRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<Comm_Board> boardList = commRepository.findreply(comm_Board.getId());
		List<CommCommentDto> commentList = new ArrayList<>();

		for (Comm_Board comment : boardList) {
			CommCommentDto commentDto = new CommCommentDto(comment);
			commentList.add(commentDto);
		}

		return commentList;
	}

	
	  public List<CommDto> getAdminCommQna() { // 관리자 QnA 페이지에 뿌려줄 게시판 리스트를 불러온다. 
		  // 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서 
	  List<Comm_Board> commList = commRepository.findQ(); // 화면에 뿌려주기 위해 담을 Dto 리스트 작성 
	  List<CommDto> mainCommDtoList = new ArrayList<>();
	  
	  // 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업 // 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다. 
	  for (Comm_Board board : commList) {CommDto commDto = new
	  CommDto(board); mainCommDtoList.add(commDto); } return
	  mainCommDtoList; }
	 

	
	  public List<CommDto> getAdminCommMento() { // 관리자 Mento 페이지에 뿌려줄 게시판 리스트를 불러온다. 
		  // 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서 
		  List<Comm_Board> commList = commRepository.findM(); // 화면에 뿌려주기 위해 담을 Dto 리스트 작성 
	  List<CommDto> mainCommDtoList = new ArrayList<>();
	  
	  // 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업 // 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다. 
	  for (Comm_Board board : commList) { CommDto commDto = new CommDto(board); mainCommDtoList.add(commDto); } return
	  mainCommDtoList; }
	 

	public void commDelete(Long boardId) throws Exception { // 게시글 삭제 메소드
		// 게시판의 하위 테이블인 img테이블이 삭제가 안되면 게시판도 삭제가 불가능해서 게시판의 이미지들을 게시판 아이디로
		// DB에서 가져온다.
		List<Comm_Board_Img> boardImg = commImgRepository.findByBoardIdOrderByIdAsc(boardId);
		for (Comm_Board_Img comm_Board_Img : boardImg) {
			// 가져온 리스트에서 이미지들을 하나하나씩 삭제해준다.
			commImgRepository.deleteById(comm_Board_Img.getId());
		}
		// 이미지가 전부 삭제되어 게시글도 삭제가 가능하기에 바로 삭제
		commRepository.deleteById(boardId);
	}

	public void commBlock(Long boardId) throws Exception { // 게시글 차단 메소드
		Comm_Board comm_Board = commRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		comm_Board.setBlockComment("Y");
	}
}
