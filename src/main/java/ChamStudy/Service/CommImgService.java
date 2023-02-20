package ChamStudy.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Repository.CommImgRepository;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service 
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려준다.
@RequiredArgsConstructor
@Component
public class CommImgService {
	private final CommImgRepository commImgRepository;
	private final CommRepository commRepository;
	
	//게시글 아이디로 해당 게시물의 이미지들을 찾아와주는 메소드
	public List<Comm_Board_Img> findImg(Long boardId) throws Exception {
		List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(boardId);
		return board_Img;
	}
	
	
}
