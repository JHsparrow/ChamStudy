package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsInformListDto {
	
	private Long id;
	
	private String title;
	
	private Integer viewCount;
	
	private String update;
	
	@QueryProjection
	public CsInformListDto(Long id, String title, Integer viewCount, String update) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.update = update;
	}
}
