package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.ClassReviewListDto;
import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.Class_review;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.ApplyListRepository;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.Class_reviewRepository;
import ChamStudy.Repository.UserRepository;
import antlr.StringUtils;
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
	
	//강의에 해당하는 리뷰전체조회
	@Transactional(readOnly = true)
	public List<ClassReviewListDto> getClassInfoReviews(Class_reviewDto class_reviewDto) {
		List<ClassReviewListDto> reviews = class_reviewRepository.findByReviewList(class_reviewDto);
		
		if (reviews == null) {
			reviews = new ArrayList<ClassReviewListDto>();
		}
		
		return reviews;
	}
	
	//리뷰 삭제
	public void deleteReview(Long reviewId) {
		Optional<Class_review> class_Review = class_reviewRepository.findById(reviewId);
		
		if(class_Review != null) {
			class_reviewRepository.deleteById(reviewId);
		}
	}
	
	/**
	 * 로그인 사용자가 작성한 리뷰인지 확인
	 * @param reviewId
	 * @param session
	 * @return
	 * false : 로그인 사용자가 작성한 리뷰가 아니다
	 * true : 로그인 사용자가 작성한 리뷰이다
	 */
	@Transactional(readOnly = true)
	public boolean validateReview(Long reviewId, UserInfo session) {
		Class_review getReview = class_reviewRepository.findByIdAndUserInfoId(reviewId, session.getId());
		
		boolean result = false;
		
		if (getReview == null) {
			result = false;
		} else {
			result = true;
		}
		
		return result;
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
	
	//리뷰아이디 조회
	public Class_reviewDto getReviewId(Long reviewId) {
		Class_review class_Review = class_reviewRepository.findById(reviewId)
				.orElseThrow(EntityNotFoundException::new);
		
		Class_reviewDto classReviewDto = Class_reviewDto.of(class_Review);
		return classReviewDto;
	}
	

}
