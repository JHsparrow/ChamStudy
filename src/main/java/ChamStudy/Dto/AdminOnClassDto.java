package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOnClassDto {
	
	private Long id; //클래스 번호
	
	private String name; //강의이름
	
	private int price; //수강료
	
}
