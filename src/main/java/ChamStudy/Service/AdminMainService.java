package ChamStudy.Service;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.ChartInterface;
import ChamStudy.Dto.EducationInfoDto;
import ChamStudy.Dto.EducationInfoInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.modifySubCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Entity.Visitor;
import ChamStudy.Repository.ApplyRepository;
import ChamStudy.Repository.CategoryRepository;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.SubCategoryRepository;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class AdminMainService { 
	
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final UserRepository userRepository;
	private final VisitorRepository visitorRepository;
	private final ClassInfoRepository classInfoRepository;
	private final ApplyRepository applyRepository;
	
	public void addCount() {
		Visitor visitor = new Visitor();
		visitorRepository.save(visitor);
	}
	
	public Long countVisitor() {
		return visitorRepository.count();
	}
	public Long countMember() {
		return userRepository.count();
	}
	
	public Long countClass() {
		return classInfoRepository.count();
	}
	
	public Long countSubCategoryIt() {
		return subCategoryRepository.count();
	}
	
	public UserInfo findByEmail(String email) {
		return userRepository.findByemail(email);
	}
	
	public Long countApplyList() {
		return applyRepository.count();
	}
	public List<ChartInterface> countChartMain(){
		return applyRepository.countChartMain();
	}
	
	public List<ChartInterface> countChartSubIt(){
		return applyRepository.countChartSubIt();
	}
	public List<ChartInterface> countChartSubCertificate(){
		return applyRepository.countChartSubCertificate();
	}
	public List<ChartInterface> countChartSubLanguage(){
		return applyRepository.countChartSubLanguage();
	}
	
	public List<EducationInfoInterface> educationInfo(){
		return classInfoRepository.educationInfo();
	}
	
}
