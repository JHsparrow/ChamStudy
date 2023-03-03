package ChamStudy.Service;


import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.EducationInfoInterface;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Entity.Visitor;
import ChamStudy.Repository.ApplyListRepository;
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
	private final ApplyListRepository applyRepository;
	
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
	
	public Long countApplyListMainIt() { return applyRepository.countMain("IT");}
	public Long countApplyListMainLi() { return applyRepository.countMain("자격증");}
	public Long countApplyListMainLa() { return applyRepository.countMain("어학");}
	
	public Long countApplyListMainItSubJ() { return applyRepository.countMainSub("IT","자바");}
	public Long countApplyListMainItSubP() { return applyRepository.countMainSub("IT","파이썬");}
	public Long countApplyListMainItSubEt() { return applyRepository.countMainSub("IT","기타");}
	
	public Long countApplyListMainLiSubE() { return applyRepository.countMainSub("자격증","엑셀");}
	public Long countApplyListMainLiSubP() { return applyRepository.countMainSub("자격증","공무원");}
	public Long countApplyListMainLiSubEt() { return applyRepository.countMainSub("자격증","기타");}
	
	public Long countApplyListMainLaSubC() { return applyRepository.countMainSub("어학","중국어");}
	public Long countApplyListMainLaSubE() { return applyRepository.countMainSub("어학","영어");}
	public Long countApplyListMainLaSubEt() { return applyRepository.countMainSub("어학","기타");}
	
	public List<EducationInfoInterface> educationInfo(){
		return classInfoRepository.educationInfo();
	}
	
}
