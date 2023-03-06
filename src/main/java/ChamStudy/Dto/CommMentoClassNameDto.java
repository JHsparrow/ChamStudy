package ChamStudy.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommMentoClassNameDto {
	private String className;
	
	public CommMentoClassNameDto(String className) {
		this.className = className;
	}
}
