package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Repository.CommImgRepository;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional // 서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려준다.
@RequiredArgsConstructor
@Component
public class AdminCommService { // 관리자 커뮤니티 게시판 서비스
	private final CommRepository commRepository;
	private final CommImgRepository commImgRepository;

	public List<AdminMainCommDto> getAdminComm() { // 관리자 메인 페이지에 뿌려줄 게시판 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		List<Comm_Board> commList = commRepository.findF();
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<AdminMainCommDto> mainCommDtoList = new ArrayList<>();

		// 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업
		// 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다.
		for (Comm_Board board : commList) {
			AdminMainCommDto commDto = new AdminMainCommDto(board);
			List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(board.getId());
			mainCommDtoList.add(commDto);
			for (Comm_Board_Img comm_board_Img : board_Img) {
				((AdminMainCommDto) mainCommDtoList).addCommBoardImg(comm_board_Img);
			}
		}
		return mainCommDtoList;
	}

	public AdminMainCommDto getAdminCommDtl(Long boardId) { // 관리자 게시판 상세페이지에 뿌려줄 게시판 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		Comm_Board comm_Board = commRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<Comm_Board> commentList = commRepository.findByoriId(comm_Board.getOriId());		
		
		List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(comm_Board.getId());
		AdminMainCommDto mainCommDto = new AdminMainCommDto(comm_Board);
		//이미지 리스트 담아준다.
		for (Comm_Board_Img comm_board_Img : board_Img) {
			((AdminMainCommDto) mainCommDto).addCommBoardImg(comm_board_Img);
		}
		//댓글 리스트도 담아준다.
		for(Comm_Board comment: commentList) {
			mainCommDto.addCommentList(comment);
		}
		return mainCommDto;
	}

	public List<AdminMainCommDto> getAdminCommQna() { // 관리자 QnA 페이지에 뿌려줄 게시판 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		List<Comm_Board> commList = commRepository.findQ();
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<AdminMainCommDto> mainCommDtoList = new ArrayList<>();

		// 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업
		// 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다.
		for (Comm_Board board : commList) {
			AdminMainCommDto commDto = new AdminMainCommDto(board);
			List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(board.getId());
			mainCommDtoList.add(commDto);
			for (Comm_Board_Img comm_board_Img : board_Img) {
				((AdminMainCommDto) mainCommDtoList).addCommBoardImg(comm_board_Img);
			}
		}
		return mainCommDtoList;
	}

	public List<AdminMainCommDto> getAdminCommMento() { // 관리자 Mento 페이지에 뿌려줄 게시판 리스트를 불러온다.
		// 커뮤니티 게시판 entity 리스트에 db에서 데이터를 찾아서 넣어준다. 데이터는 모두 그리고 순서는 최신순으로 해서
		List<Comm_Board> commList = commRepository.findM();
		// 화면에 뿌려주기 위해 담을 Dto 리스트 작성
		List<AdminMainCommDto> mainCommDtoList = new ArrayList<>();

		// 담을 Dto리스트에 db에서 찾은 데이터들을 담아주는 작업
		// 이미지가 리스트로 되어있고 찾은 데이터도 리스트라 2중 for문으로 나눠주었다.
		for (Comm_Board board : commList) {
			AdminMainCommDto commDto = new AdminMainCommDto(board);
			List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(board.getId());
			mainCommDtoList.add(commDto);
			for (Comm_Board_Img comm_board_Img : board_Img) {
				((AdminMainCommDto) mainCommDtoList).addCommBoardImg(comm_board_Img);
			}
		}
		return mainCommDtoList;
	}

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
}
