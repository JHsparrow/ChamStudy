package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;

public interface CategoryRepositoryCustom {

	//회원 리스트 가져오기
	Page<CategoryDto> getMainPage(CategoryDto categoryDto, Pageable pageable);
}
