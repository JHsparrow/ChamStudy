package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommCommentDto { //커뮤니티 댓글 Dto
	public  CommCommentDto(Comm_Board comm_Board) {  
		this.id = comm_Board.getId();
		this.boardType = comm_Board.getBoardType();
		this.Title = comm_Board.getTitle();
		this.substance = comm_Board.getSubstance();
		this.gubun = comm_Board.getGubun();
		this.oriId = comm_Board.getOriId();
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
	
	private Long oriId;
	
	private int viewCount;
	
	private String regdate;
	
	private String blockComment;
	
	private String openChat;
	
	private List<CommCommentDto> replyList = new ArrayList<>();
	
	public void addReplys(CommCommentDto reply) {
		replyList.add(reply);
	}


}

