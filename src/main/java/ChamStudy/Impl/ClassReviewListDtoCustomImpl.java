package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ClassReviewListDto;
import ChamStudy.Dto.Class_reviewDto;
import ChamStudy.Entity.QClass_review;
import ChamStudy.Entity.QUserInfo;

public class ClassReviewListDtoCustomImpl implements ClassReviewListDtoCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ClassReviewListDtoCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
	
	@Override
	public List<ClassReviewListDto> findByReviewList(Class_reviewDto class_reviewDto) {
		QClass_review classReview = QClass_review.class_review;
		QUserInfo userInfo = QUserInfo.userInfo;
		
		List<ClassReviewListDto> classReviewList = queryFactory
				.select(Projections.constructor(ClassReviewListDto.class,
						classReview.id, classReview.description, classReview.starPoint,
						classReview.regDate.as("date"), userInfo.name))
				.from(classReview)
				.join(userInfo).on(classReview.userInfo.id.eq(userInfo.id))
				.where(classReview.classInfo.id.eq(class_reviewDto.getClassId()))
				.orderBy(QClass_review.class_review.id.desc())
				.fetch();
		
		return classReviewList;
	}

	@Override
	public long updateByReview(Class_reviewDto class_reviewDto) {
		QClass_review classReview = QClass_review.class_review;
		QUserInfo userInfo = QUserInfo.userInfo;
		
		long review = queryFactory
				.update(classReview)
				.set(classReview.starPoint, class_reviewDto.getStarPoint())
				.set(classReview.description, class_reviewDto.getDescription())
				.where(classReview.id.eq(class_reviewDto.getId()))
				.execute();
		return review;
	}

}