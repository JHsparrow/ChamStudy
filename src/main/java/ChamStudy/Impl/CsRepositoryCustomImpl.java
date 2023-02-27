package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.CsFaqListDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.QCsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QCsInform;

public class CsRepositoryCustomImpl implements CsRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public CsRepositoryCustomImpl (EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//제목으로 검색하기
	public BooleanExpression titleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCsInform.csInform.title.like("%" + searchQuery + "%");
				
	}
	
	//공지사항 리스트 불러오기
	@Override
	public Page<CsInformListDto> getInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto,
			Pageable pageable) {
		QCsInform csInform = QCsInform.csInform;
		
		List<CsInformListDto> content = queryFactory.select(
				new QCsInformListDto (
						csInform.id,
						csInform.title,
						csInform.viewCount,
						csInform.upDate)
				)
				.from(csInform)
				.where(titleLike(userSearchDto.getSearchQuery()))
				.orderBy(csInform.id.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
				
		long total = queryFactory.select(Wildcard.count)
				.from(csInform)
				.where(titleLike(userSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
		
	}
	
	//공지사항 상단 고정 게시글만 select하기
	public BooleanExpression getFixed(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCsInform.csInform.title.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<CsInformListDto> getFixedInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto,
			Pageable pageable) {
		QCsInform csFixedInform = QCsInform.csInform;
		
		
		
		List<CsInformListDto> content = queryFactory.select(
				new QCsInformListDto (
						csFixedInform.id,
						csFixedInform.title,
						csFixedInform.viewCount,
						csFixedInform.upDate)
				)
				.from(csFixedInform)
				.where(csFixedInform.gubun.eq("f"))
				.orderBy(csFixedInform.id.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
		
				
		long total = queryFactory.select(Wildcard.count)
				.from(csFixedInform)
				.where(csFixedInform.gubun.eq("f"))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
	//자주묻는질문 리스트 불러오기
	@Override
	public Page<CsFaqListDto> getFaqList(UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
