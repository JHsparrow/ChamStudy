package ChamStudy.Service;



import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Entity.Visitor;
import ChamStudy.Repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class HomeMainService { 
	
	private final VisitorRepository visitorRepository;
	
	public void addCount() {
		Visitor visitor = new Visitor();
		visitorRepository.save(visitor);
	}
	
	
	
	
}
