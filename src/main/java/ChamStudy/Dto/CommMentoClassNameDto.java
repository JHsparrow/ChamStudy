package ChamStudy.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommMentoClassNameDto {
	private String className; //수료한 강의 이름
	
	public CommMentoClassNameDto(String className) {
		this.className = className;
	}
}
