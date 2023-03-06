package ChamStudy.Impl;

import java.util.List;

import ChamStudy.Dto.ClassReviewListDto;
import ChamStudy.Dto.Class_reviewDto;

public interface ClassReviewListDtoCustom {
	List<ClassReviewListDto> findByReviewList(Class_reviewDto class_reviewDto);
	
	long updateByReview(Class_reviewDto class_reviewDto);
}
