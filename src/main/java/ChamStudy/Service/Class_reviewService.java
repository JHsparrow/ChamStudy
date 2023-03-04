package ChamStudy.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.Class_reviewRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class Class_reviewService {
	
	private final ClassInfoRepository classInfoRepository;
	private final UserRepository userRepository;
	private final Class_reviewRepository class_reviewRepository;
	
	public Long reviews(Class_reviewDto class_reviewDto, UserInfo session) {
//		ClassInfo classInfo = classInfoRepository.findById(class_reviewDto.getClassId())
//				 .orElseThrow(EntityNotFoundException::new);
		return null;
	}

}
