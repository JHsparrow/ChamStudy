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
import ChamStudy.Dto.QCategoryDto;
import ChamStudy.Dto.QUserListDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class SubCategoryRepositoryCustomImpl implements SubCategoryRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public SubCategoryRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	@Override
	public Page<SubCategoryDto> getSubPage(SubCategoryDto categoryDto, Pageable pageable, Category mainId) {
		QSubCategory subcategory = QSubCategory.subCategory;
		
		List<SubCategoryDto> categories = queryFactory
			    .select(Projections.constructor(SubCategoryDto.class, subcategory.id, subcategory.name, subcategory.regDate.as("date")
			    		,subcategory.imgUrl, subcategory.oriImgName
			            ))
			    .from(subcategory)
			    .where(subcategory.categoryId.eq(mainId))
			    .offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
			    .fetch();
				
		long total = queryFactory.select(Wildcard.count)
				.from(subcategory)
				.where(subcategory.categoryId.eq(mainId))
				.fetchOne();
				
		return new PageImpl<>(categories, pageable, total);
	}
}
