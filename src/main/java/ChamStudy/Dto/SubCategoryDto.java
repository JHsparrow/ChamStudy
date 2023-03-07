package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubCategoryDto {
	private Long id;
	private String name;
	private String regDate;
	private String oriImgName;
	private String imgName;
	private String imgUrl;
	
	public SubCategoryDto() {};
	
	public SubCategoryDto(Long id, String name, String regDate) {
		this.id = id;
		this.name = name;
		this.regDate = regDate;
	}
	
	public SubCategoryDto(Long id, String name, String regDate, String imgUrl, String oriImgName) {
		this.id = id;
		this.name = name;
		this.regDate = regDate;
		this.imgUrl = imgUrl;
		this.oriImgName = oriImgName;
	}
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static SubCategoryDto of (SubCategory subCategory) {
		return modelMapper.map(subCategory, SubCategoryDto.class);
	}
	
	public SubCategoryDto(SubCategory subCategory) {
		this.id = subCategory.getId();
		this.name = subCategory.getName();
		this.regDate = subCategory.getRegDate();
		this.oriImgName = subCategory.getOriImgName();
		this.imgName = subCategory.getImgName();
		this.imgUrl = subCategory.getImgUrl();
	}
	
}