package ChamStudy.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Repository.ApplyListRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyClassService {
	private final ApplyListRepository applyListRepository;
	
	public Page<MyClassLearningDto> getLearningPage(MyClassLearningDto classLearningDto,Pageable pageable,MyClassLearningSearchDto classLearningSearchDto,String email){
		return applyListRepository.getLearningDto(classLearningDto, pageable,classLearningSearchDto,email);
	}

}
