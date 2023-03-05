package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminApplyClassListDto {

	private Long id; //수강신청리스트아이디
	
	private String name;
	
	private String sdate;
	
	private String edate;
	
	private Long count;
	
	private Long progress; //수료여부
	
	@QueryProjection	
    public AdminApplyClassListDto(Long id, String name, String sdate, String edate, Long count, Long progress){
        this.id = id;
        this.name = name;
        this.sdate = sdate;
        this.edate = edate;
        this.count = count;
        this.progress = progress;
    }
	
}
