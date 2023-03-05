package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminApplySubListDto {

	private Long id; //수강신청리스트아이디
	
	private String className;
	
	private String userName; //회원 아이디
	
	private String sDate;
	
	private String eDate;
	
	private Long progress;
	
	private String comFlag; //수료여부
	
	@QueryProjection	
    public AdminApplySubListDto(Long id, String className, String userName, String sDate, String eDate, Long progress, String comFlag){
        this.id = id;
        this.className = className;
        this.userName = userName;
        this.sDate = sDate;
        this.eDate = eDate;
        this.progress = progress;
        this.comFlag = comFlag;
    }
	
}
