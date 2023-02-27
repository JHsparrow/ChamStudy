package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Entity.Category;

public interface SubCategoryRepositoryCustom {

	//회원 리스트 가져오기
	Page<SubCategoryDto> getSubPage(SubCategoryDto categoryDto, Pageable pageable, Category mainId);
}
