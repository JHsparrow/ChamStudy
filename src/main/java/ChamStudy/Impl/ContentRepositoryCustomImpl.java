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
import ChamStudy.Dto.ContentDto;
import ChamStudy.Dto.QCategoryDto;
import ChamStudy.Dto.QUserListDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QStudyHistory;
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class ContentRepositoryCustomImpl implements ContentRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ContentRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	@Override
	public Page<ContentDto> getContentPage(ContentDto contentDto, Pageable pageable) {
		QContentInfo contentInfo = QContentInfo.contentInfo;
		QStudyHistory studyHistory = QStudyHistory.studyHistory;
		
		List<ContentDto> contents = queryFactory
			    .select(Projections.constructor(ContentDto.class, contentInfo.id, contentInfo.name, contentInfo.regDate.as("date"),
			    		studyHistory.count()))
			    .from(contentInfo)
			    .leftJoin(studyHistory).on(contentInfo.id.eq(studyHistory.contentId.id))
			    .groupBy(contentInfo.id)
			    .offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
			    .fetch();
				
				
		
		long total = queryFactory.select(Wildcard.count)
				.from(contentInfo)
				.fetchOne();
				
		return new PageImpl<>(contents, pageable, total);
	}
}
