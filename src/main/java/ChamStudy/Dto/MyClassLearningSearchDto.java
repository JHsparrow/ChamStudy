package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyClassLearningSearchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
	private String searchCategory;
}
