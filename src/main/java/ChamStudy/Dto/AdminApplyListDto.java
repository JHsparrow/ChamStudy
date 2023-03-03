package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminApplyListDto {

	private Long id; //수강신청리스트아이디
	
	private String className;
	
	private Long userName; //회원 아이디
	
	private String sDate;
	
	private String eDate;
	
	private Long progress;
	
	private String comFlag; //수료여부
	
}
