package ChamStudy.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
	
	public List<Category> getAllMainList(){
		return categoryRepository.findAll();
	}
	
	public List<SubCategory> getAllSubList(Long mainId){
		return subCategoryRepository.findSubCategoryMain(mainId);
	}
	
	public void saveCategory(String cataName) throws Exception {
		Category category = new Category();
		
		category.setName(cataName);
		categoryRepository.save(category);
		
	}
}
