package ChamStudy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EducationInfoDto {
	
	private Long className;
	
	private String progressRate; 
	
	private String completionRate;
}

