package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassReviewListDto {
	private Long id;
	
	private String description;
	
	private Integer starPoint;
	
	private String regDate;
	
	private String userName;
	
	@QueryProjection
	public ClassReviewListDto(Long id, String description, Integer starPoint, 
			String regDate, String userName) {
		this.id = id;
		this.description = description;
		this.starPoint = starPoint;
		this.regDate = regDate;
		this.userName = userName;
	}
}
