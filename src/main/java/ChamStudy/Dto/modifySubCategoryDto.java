package ChamStudy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class modifySubCategoryDto {
	private Long mainId;
	private Long subId;
	private String cateName;
	private String subImg;
}
