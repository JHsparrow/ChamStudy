package ChamStudy.Dto;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMainCommDto {

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
	
	private Comm_Board_Img comm_Board_Img;
	
	public void addCommBoardImg(Comm_Board_Img board_Img) {
		this.comm_Board_Img = board_Img;
	}
}
