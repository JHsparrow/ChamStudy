package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.Comm_Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommWriteFormDto {

	private Long id;
	
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String Title; //제목
	
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String substance; //내용
	
	private String openChat; //오픈채팅
	
	private String boardType; //게시판 종류

	private String gubun; //글 구분

	private String blockComment; //글 차단 여부
	
	private String viewCount; //조회수
	
	private String className; //강의명
	
	private List<CommImgDto> commImgDtoList = new ArrayList<>();// 게시판에 담을 이미지들
	
	private List<Long> commImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Comm_Board createBoard() {
		return modelMapper.map(this, Comm_Board.class);
	}
	
	public static CommWriteFormDto of (Comm_Board board) {
		return modelMapper.map(board, CommWriteFormDto.class);
	}
	
}
