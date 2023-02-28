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
	
	private Long reportedId; //신고한 사람
	
	private String warnType; // 신고유형
	
	private String description;
	
	private String boardType;
	
	private String boardId;
	
	private String regDate;
	
	
	
}
