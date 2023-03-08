package ChamStudy.Service;


import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Dto.SubCategoryDto;
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
	
	@Value("${csImgLocation}")
	private String csImgLocation;
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final FileService fileService;
	
	public Page<CategoryDto> getAllMainList(CategoryDto categoryDto,Pageable pageable){
		return categoryRepository.getMainPage(categoryDto,pageable);
	}
	
	public Page<SubCategoryDto> getSubList(SubCategoryDto categoryDto,Pageable pageable, Category mainId){
		return subCategoryRepository.getSubPage(categoryDto,pageable, mainId);
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
	
	//서브카테고리 생성
	public SubCategory saveSubCategory(Category mainId, String cataName) throws Exception {
		SubCategory category = new SubCategory();
		category.setCategoryId(mainId);
		category.setName(cataName);
		
		subCategoryRepository.save(category);
		
		return category;
	}
	
	//서브카테고리 생성 - 이미지 등록
	public void saveSubCategoryImg(SubCategory subCategory, MultipartFile subImg, Long subId) throws Exception {
		SubCategory subcategory = subCategoryRepository.findById(subId).orElseThrow();
		String oriImgName = subImg.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(csImgLocation, oriImgName, subImg.getBytes());
			imgUrl = "/contents/img/" + imgName;
		} else {
			oriImgName = subcategory.getOriImgName();
			imgName = subcategory.getImgName();
			imgUrl = subcategory.getImgUrl();
		}
		
		subCategory.updateImg(oriImgName, imgName, imgUrl);
		subCategoryRepository.save(subCategory);
	}
	
	public void updateMainCategory(Long mainId ,String cateName) throws Exception {
		Category category = categoryRepository.findById(mainId).orElseThrow(EntityNotFoundException::new);   
		category.updateMainCategory(cateName);
		categoryRepository.save(category);
	}
	
	//서브카테고리 수정
	public SubCategory updateSubCategory(modifySubCategoryDto modifySubCategoryDto) throws Exception {
		SubCategory subCategory = subCategoryRepository.findById(modifySubCategoryDto.getSubId()).orElseThrow(EntityNotFoundException::new);  
		
		subCategory.updateSubCategory(modifySubCategoryDto);
		subCategoryRepository.save(subCategory);
		return subCategory;
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
