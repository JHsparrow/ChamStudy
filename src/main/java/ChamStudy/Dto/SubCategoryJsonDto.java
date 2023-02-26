package ChamStudy.Dto;

import ChamStudy.Entity.SubCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryJsonDto {
	private Long id;
	private String name;
	
	public SubCategoryJsonDto(SubCategory subCategory) {
		this.id = subCategory.getId();
		this.name = subCategory.getName();
	}
	
}
