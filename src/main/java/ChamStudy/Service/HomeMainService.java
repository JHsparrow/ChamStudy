package ChamStudy.Service;



import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.MainFormDto;
import ChamStudy.Entity.Visitor;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class HomeMainService { 
	
	private final VisitorRepository visitorRepository;
	private final ClassInfoRepository classInfoRepository;
	
	public void addCount() {
		Visitor visitor = new Visitor();
		visitorRepository.save(visitor);
	}
	
	@Transactional(readOnly = true)
	public List<MainFormDto> getMainClass() {
		return classInfoRepository.getMainClassInfo();
	}
	
	
	
}
