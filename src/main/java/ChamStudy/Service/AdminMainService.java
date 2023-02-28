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
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.modifySubCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Entity.Visitor;
import ChamStudy.Repository.CategoryRepository;
import ChamStudy.Repository.SubCategoryRepository;
import ChamStudy.Repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class AdminMainService { 
	
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final VisitorRepository visitorRepository;
	
	public void addCount() {
		Visitor visitor = new Visitor();
		visitorRepository.save(visitor);
	}
	
	public Integer countVisitor() {
		return visitorRepository.findCountVisitor();
	}
	
	
	
	
	
}
