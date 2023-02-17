package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMainCommDto { //커뮤니티 관리자 페이지 DTO

	//service에서 view에 보여줄 entity들 여기 담아서 view로 보내준다.
	public AdminMainCommDto(Comm_Board comm_Board) {  
		this.id = comm_Board.getId();
		this.boardType = comm_Board.getBoardType();
		this.Title = comm_Board.getTitle();
		this.substance = comm_Board.getSubstance();
		this.gubun = comm_Board.getGubun();
		this.oriId = comm_Board.getOriId();
		this.viewCount = comm_Board.getViewCount();
		this.userInfo = comm_Board.getUserId();
	}
	private int id;
	
	private String Title;
	
	private String boardType;
	
	private UserInfo userInfo;
	
	private String gubun;
	
	private String substance;
	
	private int oriId;
	
	private int viewCount;
	
	private List<Comm_Board_Img> comm_Board_Img = new ArrayList<>();
	
	//이미지는 따로 담아야해서 따로 메소드를 만들어주었다.
	public void addCommBoardImg(Comm_Board_Img board_Img) {
		this.comm_Board_Img.add(board_Img);
	}
}
