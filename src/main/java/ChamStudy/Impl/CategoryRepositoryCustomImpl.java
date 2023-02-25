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
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.QCategory;
import ChamStudy.Entity.QSubCategory;
import ChamStudy.Entity.QUserInfo;

public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public CategoryRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	@Override
	public Page<CategoryDto> getMainPage(CategoryDto categoryDto, Pageable pageable) {
		QCategory category = QCategory.category;
		QSubCategory subCategory = QSubCategory.subCategory;
		
		List<CategoryDto> categories = queryFactory
			    .select(Projections.constructor(CategoryDto.class, category.id, category.name, category.regDate.as("date"),
			            subCategory.count()))
			    .from(category)
			    .leftJoin(subCategory).on(category.id.eq(subCategory.categoryId.id))
			    .groupBy(category.id)
			    .offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
			    .fetch();
				
				
		
		long total = queryFactory.select(Wildcard.count)
				.from(category)
				.fetchOne();
				
		return new PageImpl<>(categories, pageable, total);
	}
}
