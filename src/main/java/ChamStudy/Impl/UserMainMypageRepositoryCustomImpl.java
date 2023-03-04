package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.QCompletionListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QCompletion;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QStudyResult;
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class UserMainMypageRepositoryCustomImpl implements UserMainMypageRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public UserMainMypageRepositoryCustomImpl (EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//메인 카테고리 필터
	public BooleanExpression categoryLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCategory.category.name.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<CompletionListDto> getCompletionList(UserSearchDto userSearchDto, CompletionListDto completionListDto,
			Pageable pageable, Long userId) {
		QContentInfo contentInfo = QContentInfo.contentInfo;
		QCategory category = QCategory.category;
		QSubCategory subCategory = QSubCategory.subCategory;
		QClassInfo classInfo = QClassInfo.classInfo;
		QStudyResult studyResult = QStudyResult.studyResult;
		QCompletion completion = QCompletion.completion;
		QApplyList applyList = QApplyList.applyList;
		QUserInfo userInfo = QUserInfo.userInfo;
		
		List<CompletionListDto> content = queryFactory.select(
				new QCompletionListDto (
						completion.id,
						category.name,
						subCategory.name,
						classInfo.name,
						studyResult.progress,
						studyResult.regDate,
						completion.regDate,
						contentInfo.id,
						contentInfo.imgUrl,
						contentInfo.oriImgName
						)
				)
				.from(completion)
				.join(completion.resultId, studyResult)
				.join(studyResult.applyId, applyList)
				.join(applyList.classInfo, classInfo)
				.join(classInfo.contentInfo, contentInfo)
				.join(contentInfo.categoryId, category)
				.join(contentInfo.subCategoryId, subCategory)
				.join(applyList.userInfo, userInfo)
				.where(userInfo.id.eq(userId))
				.where(categoryLike(userSearchDto.getSearchQuery()))
				.orderBy(completion.regDate.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
				
		long total = queryFactory.select(Wildcard.count)
				.from(completion)
				.where(categoryLike(userSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
}
