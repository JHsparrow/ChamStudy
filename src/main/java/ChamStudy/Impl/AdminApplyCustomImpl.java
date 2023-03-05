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

import ChamStudy.Dto.AdminApplyClassListDto;
import ChamStudy.Dto.AdminApplySubListDto;
import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.QCategoryDto;
import ChamStudy.Dto.QUserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QStudyResult;
import ChamStudy.Entity.QUserInfo;

public class AdminApplyCustomImpl implements AdminApplyCustom{

	private JPAQueryFactory queryFactory;
	
	public AdminApplyCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression nameLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QUserInfo.userInfo.name.like("%" + searchQuery + "%");
	}
	
		
	@Override
	public Page<AdminApplySubListDto> getAdminApplySubList(AdminApplySubListDto adminApplySubListDto , Pageable pageable, UserSearchDto userSearchDto, Long classId) {
		QApplyList applyList = QApplyList.applyList;
		QClassInfo classInfo = QClassInfo.classInfo;
		QUserInfo userInfo = QUserInfo.userInfo;
		QStudyResult studyResult = QStudyResult.studyResult;
		
		List<AdminApplySubListDto> content = queryFactory
			    .select(Projections.constructor(AdminApplySubListDto.class, 
			    		applyList.id,
			    		classInfo.name,
			    		userInfo.name,
			    		applyList.regDate,
		                classInfo.eDate,
		                studyResult.progress,
		                applyList.comFlag))
		            .from(applyList)
		            .join(classInfo).on(applyList.classInfo.id.eq(classInfo.id))
		            .join(userInfo).on(applyList.userInfo.id.eq(userInfo.id))
		            .leftJoin(studyResult).on(applyList.id.eq(studyResult.applyId.id))
		            .where(nameLike(userSearchDto.getSearchQuery()))
		            .where(classInfo.id.eq(classId))
		            .orderBy(classInfo.name.asc())
		            .offset(pageable.getOffset())	//데이터를 가져올 시작 index
					.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
		            .fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(applyList)
				.join(classInfo).on(applyList.classInfo.id.eq(classInfo.id))
	            .join(userInfo).on(applyList.userInfo.id.eq(userInfo.id))
	            .leftJoin(studyResult).on(applyList.id.eq(studyResult.applyId.id))
	            .where(nameLike(userSearchDto.getSearchQuery()))
	            .where(classInfo.id.eq(classId))
				.fetchOne();
				
		return new PageImpl<>(content, pageable, total);
	}
}
