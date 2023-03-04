package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Dto.QMyClassLearningDto;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QApplySeq;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QStudyResult;
import ChamStudy.Entity.UserInfo;

public class ApplyListRepositoryCustomImpl implements ApplyListRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ApplyListRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Long saveApplyClass(ApplyListDto applyListDto, UserInfo session) {
		QApplySeq applySeq = QApplySeq.applySeq;
		QApplyList applyList = QApplyList.applyList;
		
		//현재 시퀀스 값 +1 조회
		long applyId = queryFactory
				.select(applySeq.nextVal)
				.from(applySeq)
				.fetchOne();
		
		//현재 시퀀스 값 +1 수정
		applyId++;
		
		long updCnt = queryFactory		
                .update(applySeq) //update apply_seq
                .set(applySeq.nextVal, applyId)
                .execute(); //update 실행
		
		
		//나의 강의실 insert
		long insCnt = queryFactory
				.insert(applyList)
				//.columns(applyList.id, applyList.comFlag, applyList.userInfo.id, applyList.classInfo.id, applyList.regDate)
			    //.values(applyId, "N", session.getId(), applyListDto.getClassId(), Expressions.dateTimeTemplate(LocalDateTime.class,"DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
			    //.values(applyId, "N", session.getId(), applyListDto.getClassId(), Expressions.stringTemplate("DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
				//.values(applyId, "N", session.getId(), applyListDto.getClassId(), "2023년 03월 03일")
				//.set(applyList.id, applyId)
				//.set(applyList.comFlag, "N")
				//.set(applyList.userInfo.id, session.getId())
				//.set(applyList.classInfo.id, applyListDto.getClassId())
				//.set(applyList.regDate, Expressions.stringTemplate("DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
				//.execute()
				.set(applyList.id, 1000l)
				.set(applyList.comFlag, "N")
				.set(applyList.userInfo.id, 1001l)
				.set(applyList.classInfo.id, 1002l)
				.set(applyList.regDate, "2023년 03월 02일")
				.execute();
		
		return applyId;
	}

	public BooleanExpression selectCategory(String searchCategory) {
		if(searchCategory == null || searchCategory.equals("IT")) {
			return null;
		}
		return QCategory.category.name.eq(searchCategory);
	}
	
	@Override
	public Page<MyClassLearningDto> getLearningDto(MyClassLearningDto classLearningDto, Pageable pageable,MyClassLearningSearchDto classLearningSearchDto,String email) {
		QApplyList apply  = QApplyList.applyList;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		QCategory category = QCategory.category;
		QStudyResult studyResult = QStudyResult.studyResult;
		
		List<MyClassLearningDto> content = queryFactory
				.select(new QMyClassLearningDto(
									apply.id,
									apply.regDate,
									apply.userInfo,
									apply.classInfo,
									contentInfo.imgUrl,
									category.name,
									studyResult.progress)
						)
						.from(apply)
						.innerJoin(contentInfo)
						.on(apply.classInfo.contentInfo.id.eq(contentInfo.id))
						.innerJoin(category)
						.on(apply.id.eq(category.id))
						.innerJoin(studyResult)
						.on(apply.id.eq(studyResult.applyId.id))
						.where(apply.userInfo.email.eq(email))
						.where(selectCategory(classLearningSearchDto.getSearchCategory()))
						.orderBy(apply.regDate.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total = queryFactory.select(Wildcard.count)
				.from(apply)
				.where(apply.userInfo.email.eq(email))
				.where(selectCategory(classLearningSearchDto.getSearchCategory()))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}
	
	
}
