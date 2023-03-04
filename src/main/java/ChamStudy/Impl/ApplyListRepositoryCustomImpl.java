package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Dto.QMyClassLearningDto;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QStudyResult;

public class ApplyListRepositoryCustomImpl implements ApplyListRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ApplyListRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
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
		QClassInfo classInfo = QClassInfo.classInfo;
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
						.join(classInfo).on(apply.classInfo.id.eq(classInfo.id))
						.innerJoin(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
						.innerJoin(category).on(contentInfo.categoryId.id.eq(category.id))
						.innerJoin(studyResult).on(apply.id.eq(studyResult.applyId.id))
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
