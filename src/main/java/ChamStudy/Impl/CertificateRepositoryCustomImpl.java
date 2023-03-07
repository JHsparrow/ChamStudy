package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.CertificateDto;
import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.QCategoryDto;
import ChamStudy.Dto.QUserListDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QCompletion;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QStudyResult;
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class CertificateRepositoryCustomImpl implements CertificateRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public CertificateRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<CertificateDto> getCompletionPage(CertificateDto certificateDto, Pageable pageable,String email) {
		QCompletion completion = QCompletion.completion;
		QStudyResult studyResult = QStudyResult.studyResult;
		QApplyList applyList = QApplyList.applyList;
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		QUserInfo userInfo = QUserInfo.userInfo;
		
		List<CertificateDto> content = queryFactory
				.select(Projections.constructor(
						CertificateDto.class,
						completion.id,
						classInfo.name.as("name"),
						completion.regDate.as("date"),
						contentInfo.imgUrl.as("img")
						))
				.from(completion)
				.join(studyResult).on(studyResult.id.eq(completion.resultId.id))
				.join(applyList).on(applyList.id.eq(studyResult.applyId.id))
				.join(classInfo).on(classInfo.id.eq(applyList.classInfo.id))
				.join(contentInfo).on(contentInfo.id.eq(classInfo.contentInfo.id))
				.join(userInfo).on(applyList.userInfo.id.eq(userInfo.id))
				.where(userInfo.email.eq(email))
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
			    .fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(completion)
				.join(studyResult).on(studyResult.id.eq(completion.resultId.id))
				.join(applyList).on(applyList.id.eq(studyResult.applyId.id))
				.join(classInfo).on(classInfo.id.eq(applyList.classInfo.id))
				.join(contentInfo).on(contentInfo.id.eq(classInfo.contentInfo.id))
				.join(userInfo).on(applyList.userInfo.id.eq(userInfo.id))
				.where(userInfo.email.eq(email))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}
	
	
	
}
