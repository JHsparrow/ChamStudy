package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;


import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommDto { //커뮤니티 관리자 페이지 DTO

	//service에서 view에 보여줄 entity들 여기 담아서 view로 보내준다.
	public CommDto(Comm_Board comm_Board) {  
		this.id = comm_Board.getId();
		this.boardType = comm_Board.getBoardType();
		this.Title = comm_Board.getTitle();
		this.substance = comm_Board.getSubstance();
		this.gubun = comm_Board.getGubun();
		this.viewCount = comm_Board.getViewCount();
		this.userInfo = comm_Board.getUserId();
		this.regdate = comm_Board.getRegDate();
		this.blockComment = comm_Board.getBlockComment();
		this.openChat = comm_Board.getOpenChat();
	}
	private Long id;
	
	private String Title;
	
	private String boardType;
	
	private UserInfo userInfo;
	
	private String gubun;
	
	private String substance;
	
	private int viewCount;
	
	private String regdate;
	
	private String blockComment;
	
	private String openChat;
	
	private List<CommImgDto> commImgDtos = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	//이미지는 따로 담아야해서 따로 메소드를 만들어주었다.
	public static CommImgDto of(Comm_Board board) {
		return modelMapper.map(board, CommImgDto.class);
	}
	
}