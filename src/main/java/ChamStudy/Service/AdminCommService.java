package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Repository.CommImgRepository;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service 
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려준다.
@RequiredArgsConstructor
@Component
public class AdminCommService {
	private final CommRepository commRepository;
	private final CommImgRepository commImgRepository;
	
	public List<AdminMainCommDto> getAdminComm(){
		List<Comm_Board> commList = commRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
		List<AdminMainCommDto> mainCommDtoList = new ArrayList<>();
		
		for(Comm_Board board : commList) {
			AdminMainCommDto commDto = new AdminMainCommDto(board);
			List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(board.getId());
			mainCommDtoList.add(commDto);
			for(Comm_Board_Img comm_board_Img : board_Img) {
				((AdminMainCommDto) mainCommDtoList).addCommBoardImg(comm_board_Img);
			}
		}
		return mainCommDtoList;
	}
}
