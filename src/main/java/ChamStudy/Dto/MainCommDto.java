package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCommDto { //커뮤니티 관리자 페이지 DTO

	//service에서 view에 보여줄 entity들 여기 담아서 view로 보내준다.
	
	private Long id;
	
	private String Title;
	
	private String boardType;
	
	private UserInfo userInfo;
	
	private String gubun;
	
	private String substance;
	
	private Integer viewCount;
	
	private String regDate;
	
	private String blockComment;
	
	private String openChat;
	
	private String imgUrl;
	
	@QueryProjection
	public MainCommDto(Long id, String Title, String boardType, UserInfo userInfo, String gubun, String substance, Integer viewCount, String regDate, String blockComment, String openChat, String imgUrl) {  
		this.id = id;
		this.Title = Title;
		this.boardType = boardType;
		this.userInfo = userInfo;
		this.gubun = gubun;
		this.substance = substance;
		this.viewCount = viewCount;
		this.regDate = regDate;
		this.blockComment = blockComment;
		this.openChat = openChat;
		this.imgUrl = imgUrl;
	}
	
}