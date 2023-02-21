package ChamStudy.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Entity.Category;
import ChamStudy.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class AdminCategoryService { 
	
	private final CategoryRepository categoryRepository;
	
	public String saveCategory(String cataName) throws Exception {
		System.out.println("123");
		Category category = new Category();
		
		category.setName(cataName);
		categoryRepository.save(category);
		return "123";
		
	}
}
