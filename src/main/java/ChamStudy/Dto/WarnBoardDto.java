package ChamStudy.Dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Entity.CsFaq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarnBoardDto {
	
	private Long id;
	
	private String reporterId; //신고 받는사람
	
	private String reportedId; //신고한 사람
	
	@NotBlank(message = "카테고리를 선택해 주십시오.")
	private String warnType; // 신고유형
	
	private String description;
	
	private String userInfo;
	
	private String boardType;
	
	private String boardId;
	
	private String regDate;
	
	
	@QueryProjection	//querydsl로 결과 조회 시 MainItemDto객체로 바로 받아올 수 있음
    public WarnBoardDto(Long id, String reporterId, String reportedId, String warnType, String description, String userInfo, String boardType, String boardId, String regDate){
        this.id = id;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
        this.warnType = warnType;
        this.description = description;
        this.userInfo = userInfo;
        this.boardType = boardType;
        this.boardId = boardId;
        this.regDate = regDate;
    }
}
