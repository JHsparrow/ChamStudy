package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsFaqListDto {
	
	private Long id;
	
	private String gubun;
	
	private String title;
	
	private String update;
	
	@QueryProjection
	public CsFaqListDto (Long id, String gubun, String title, String update) {
		this.id = id;
		this.gubun = gubun;
		this.title = title;
		this.update = update;
	}
}
