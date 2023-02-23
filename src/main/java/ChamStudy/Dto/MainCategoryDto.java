package ChamStudy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MainCategoryDto {
	private Long id;
	private String name;
	private String regDate;
	private int count;
}
