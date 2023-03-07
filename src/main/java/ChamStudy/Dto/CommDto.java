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
		this.oriId = comm_Board.getOriId();
	}
	private Long id; //게시판 아이디
	
	private String Title; //게시판 제목
	
	private String boardType; //게시판 종류(자유,멘토,QnA)
	
	private UserInfo userInfo; //사용자 정보
	
	private String gubun; //글 구분(댓글,게시판)
	
	private String substance; //글 내용
	
	private int viewCount; //조회수
	
	private String regdate; //글 올린 시간
	
	private String blockComment; //댓글 차단 여부
	
	private String openChat; //오픈 카톡 링크
	
	private Long oriId; //글 식별 아이디
	
	private List<CommImgDto> commImgDtos = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	//이미지는 따로 담아야해서 따로 메소드를 만들어주었다.
	public static CommImgDto of(Comm_Board board) {
		return modelMapper.map(board, CommImgDto.class);
	}
	
}