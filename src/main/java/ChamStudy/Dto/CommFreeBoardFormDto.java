package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.Comm_Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommFreeBoardFormDto {

	private Long id;
	
	private String Title; //제목
	
	private String substance; //내용
	
	private String openChat; //오픈채팅
	
	private List<CommImgDto> commImgDtoList = new ArrayList<>();// 게시판에 담을 이미지들
	
	private List<Long> commImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Comm_Board createBoard() {
		return modelMapper.map(this, Comm_Board.class);
	}
	
}
