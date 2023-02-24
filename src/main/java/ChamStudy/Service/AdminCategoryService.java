package ChamStudy.Service;


import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Dto.modifySubCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Repository.CategoryRepository;
import ChamStudy.Repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class AdminCategoryService { 
	
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	
	public Page<CategoryDto> getAllMainList(CategoryDto categoryDto,Pageable pageable){
		return categoryRepository.getMainPage(categoryDto,pageable);
	}
	
	
	
	public List<SubCategory> getAllSubList(Long mainId){
		return subCategoryRepository.findSubCategoryMain(mainId);
	}
	public Category getMainInfo(Long mainId){
		return categoryRepository.findMainInfo(mainId);
	}
	
	
	public void saveCategory(String cataName) throws Exception {
		Category category = new Category();
		
		category.setName(cataName);
		categoryRepository.save(category);
		
	}
	
	public void saveSubCategory(Category mainId, String cataName) throws Exception {
		SubCategory category = new SubCategory();
		category.setCategoryId(mainId);
		category.setName(cataName);
		subCategoryRepository.save(category);
		
	}
	
	public void updateMainCategory(Long mainId ,String cateName) throws Exception {
		Category category = categoryRepository.findById(mainId).orElseThrow(EntityNotFoundException::new);   
		category.updateMainCategory(cateName);
		categoryRepository.save(category);
	}
	
	public void updateSubCategory(modifySubCategoryDto modifySubCategoryDto) throws Exception {
		SubCategory subCategory = subCategoryRepository.findById(modifySubCategoryDto.getSubId()).orElseThrow(EntityNotFoundException::new);   
		subCategory.updateSubCategory(modifySubCategoryDto);
		subCategoryRepository.save(subCategory);
	}
	
	public void deleteSubCategory(Long subId) throws Exception {
		SubCategory subCategory = subCategoryRepository.findById(subId).orElseThrow(EntityNotFoundException::new);   
		subCategoryRepository.delete(subCategory);
	}
	
	public void deleteMainCategory(Long mainId) throws Exception {
		Category Category = categoryRepository.findById(mainId).orElseThrow(EntityNotFoundException::new);   
		categoryRepository.delete(Category);
	}
	
	public List<Category> getCategory() throws Exception{
		return categoryRepository.findAll();
		
	}
}
