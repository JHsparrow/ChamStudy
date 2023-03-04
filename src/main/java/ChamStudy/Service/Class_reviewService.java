package ChamStudy.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.Class_review;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.ApplyListRepository;
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
	private final ApplyListRepository applyListRepository;
		
	public Long addReview(Class_reviewDto class_reviewDto, String email) {
		//class id 에 해당하는 정보가 있는지 확인
		ClassInfo classInfo = classInfoRepository.findById(class_reviewDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		UserInfo userInfo = userRepository.findByemail(email);

		if (userInfo == null) {
			return (long) -7;
		}
		
		ApplyList savedClass = applyListRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
		if(savedClass == null){
            return (long) -9;
		}
		
		Class_review savedClass_review = class_reviewRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
        if(savedClass_review != null){
        	return (long) -777;
        } else {
        	Class_review class_reviewAdd = Class_review.createClass_review(class_reviewDto, classInfo, userInfo);
        	class_reviewRepository.save(class_reviewAdd);
            return class_reviewAdd.getId();
        }
	}
	
	//리뷰조회
	public Long getReview(Class_reviewDto class_reviewDto, UserInfo session) {
		ClassInfo classInfo = classInfoRepository.findById(class_reviewDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		UserInfo userInfo = userRepository.findById(session.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		if (userInfo == null) {
			new EntityNotFoundException("userInfo is null.");
		}
		
		Class_review getReview = class_reviewRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
		Long reviewId = (long) -1;
		
		if(getReview == null ) {
			reviewId = (long) -1;
		} else {
			reviewId = getReview.getId();
		}
		
		return reviewId;
	}

}
