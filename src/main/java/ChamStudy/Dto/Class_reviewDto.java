package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.Class_review;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Class_reviewDto {
	
	private Long id;
	
	private String description;
	
	private Integer starPoint;
	
	private Long userId;
	
	private Long classId;
	
	private String regDate;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Class_review createClass_review() {
		return modelMapper.map(this, Class_review.class);
	}
	
	public static Class_reviewDto of(Class_review class_review) {
		return modelMapper.map(class_review, Class_reviewDto.class);
	}
	
}
