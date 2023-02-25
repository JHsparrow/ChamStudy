package ChamStudy.Impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.QUserListDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QUserInfo;

public class UserRepositoryCustomImpl implements UserRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public UserRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//이름으로 찾기
	private BooleanExpression nameLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QUserInfo.userInfo.name.like("%" + searchQuery + "%");
	}
	
	
	@Override
	public Page<UserListDto> getUserPage(UserSearchDto userSearchDto,UserInfoDto userInfoDto, Pageable pageable) {
		QUserInfo userInfo = QUserInfo.userInfo;
		
		List<UserListDto> content = queryFactory.select(
				new QUserListDto(
						userInfo.id,
						userInfo.email,
						userInfo.name,
						userInfo.password,
						userInfo.phone,
						userInfo.role,
						userInfo.regDate,
						userInfo.gubun)
				)
				.from(userInfo)
				.where(nameLike(userSearchDto.getSearchQuery()))
				.orderBy(userInfo.id.asc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(userInfo)
				.where(nameLike(userSearchDto.getSearchQuery()))
				.fetchOne();
				
		return new PageImpl<>(content, pageable, total);
	}
}
