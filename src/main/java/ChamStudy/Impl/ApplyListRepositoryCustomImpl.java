package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

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
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class ApplyListRepositoryCustomImpl implements ApplyListRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ApplyListRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	public BooleanExpression categoryLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCategory.category.name.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MyClassLearningDto> getLearningDto(MyClassLearningDto classLearningDto, Pageable pageable,MyClassLearningSearchDto classLearningSearchDto,String email) {
		QApplyList apply  = QApplyList.applyList;
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		QCategory category = QCategory.category;
		QStudyResult studyResult = QStudyResult.studyResult;
		QSubCategory subCategory = QSubCategory.subCategory;
		QUserInfo userInfo = QUserInfo.userInfo;
		
		List<MyClassLearningDto> content = queryFactory
				.select(new QMyClassLearningDto(
									apply.id,
									apply.regDate,
									apply.userInfo,
									apply.classInfo,
									contentInfo.imgUrl,
									category.name,
									studyResult.progress,
									contentInfo.id,
									subCategory.name)
						)
						.from(apply)
						.join(classInfo).on(apply.classInfo.id.eq(classInfo.id))
						.innerJoin(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
						.innerJoin(category).on(contentInfo.categoryId.id.eq(category.id))
						.innerJoin(subCategory).on(contentInfo.subCategoryId.id.eq(subCategory.id))
						.leftJoin(studyResult).on(apply.id.eq(studyResult.applyId.id))
						.join(userInfo).on(apply.userInfo.id.eq(userInfo.id))
						.where(userInfo.email.eq(email))
						.where(categoryLike(classLearningSearchDto.getSearchCategory()))
						.where(apply.comFlag.eq("N"))
						.orderBy(apply.regDate.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total = queryFactory.select(Wildcard.count)
				.from(apply)
				.join(classInfo).on(apply.classInfo.id.eq(classInfo.id))
				.innerJoin(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
				.innerJoin(category).on(contentInfo.categoryId.id.eq(category.id))
				.innerJoin(subCategory).on(contentInfo.subCategoryId.id.eq(subCategory.id))
				.leftJoin(studyResult).on(apply.id.eq(studyResult.applyId.id))
				.join(userInfo).on(apply.userInfo.id.eq(userInfo.id))
				.where(userInfo.email.eq(email))
				.where(categoryLike(classLearningSearchDto.getSearchCategory()))
				.where(apply.comFlag.eq("N"))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}
	
	
}
