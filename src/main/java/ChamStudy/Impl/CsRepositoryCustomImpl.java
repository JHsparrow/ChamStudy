package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.CsFaqListDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.CsQnaListDto;
import ChamStudy.Dto.QCsFaqListDto;
import ChamStudy.Dto.QCsInformListDto;
import ChamStudy.Dto.QCsQnaListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Dto.WarnBoardDto;
import ChamStudy.Entity.CsQna;
import ChamStudy.Entity.QCsFaq;
import ChamStudy.Entity.QCsInform;
import ChamStudy.Entity.QCsQna;
import ChamStudy.Entity.QWarnBoard;

public class CsRepositoryCustomImpl implements CsRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public CsRepositoryCustomImpl (EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//공지사항 제목으로 검색하기
	public BooleanExpression titleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCsInform.csInform.title.like("%" + searchQuery + "%");
				
	}
	
	/*
	//카테고리 선택하기
	private BooleanExpression selectCategory(String searchCategory) {
		String category;
		if(StringUtils.equals("A", searchCategory) || searchCategory == null)return null; 
		else if (StringUtils.equals("", searchCategory)) category = "A";
	}
	*/
	
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
						csInform.regDate)
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
	
	//공지사항 상단 고정 게시글 불러오기
	@Override
	public Page<CsInformListDto> getFixedInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto,
			Pageable pageable) {
		QCsInform csFixedInform = QCsInform.csInform;
		
		List<CsInformListDto> content = queryFactory.select(
				new QCsInformListDto (
						csFixedInform.id,
						csFixedInform.title,
						csFixedInform.viewCount,
						csFixedInform.regDate)
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
	
	/*
	//자주묻는질문 카테고리 검색하기 (정상 ver.)
	public BooleanExpression selectCategory(String searchCategory) {
		return StringUtils.isEmpty(searchCategory) ? null : QCsFaq.csFaq.gubun.eq(searchCategory);
	}
	*/
	
	//자주묻는질문 카테고리 검색하기
	public BooleanExpression selectCategory(String searchCategory) {
		if(searchCategory == null || searchCategory.equals("A")) {
			return null;
		}
		return QCsFaq.csFaq.gubun.eq(searchCategory);
	}
	
	//자주묻는질문 제목으로 검색하기
	public BooleanExpression faqTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCsFaq.csFaq.title.like("%" + searchQuery + "%");
	}
	
	//자주묻는질문 리스트 불러오기
	@Override
	public Page<CsFaqListDto> getFaqList(UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Pageable pageable) {
		QCsFaq csFaq = QCsFaq.csFaq;
		
		List<CsFaqListDto> content = queryFactory.select(
				new QCsFaqListDto(
						csFaq.id,
						csFaq.gubun,
						csFaq.title,
						csFaq.upDate)
				)
				.from(csFaq)
				.where(selectCategory(userSearchDto.getSearchCategory()))
				.where(faqTitleLike(userSearchDto.getSearchQuery()))
				.orderBy(csFaq.id.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(csFaq)
				.where(selectCategory(userSearchDto.getSearchCategory()))
				.where(faqTitleLike(userSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	//1:1 게시판 리스트 불러오기(관리자 페이지)
	@Override
	public Page<CsQnaListDto> getQnaList(UserSearchDto userSearchDto, CsQnaListDto csQnaListDto, Pageable pageable) {
		QCsQna csQna = QCsQna.csQna;
		
//		List<CsQnaListDto> content = queryFactory.select(
//				new QCsQnaListDto(
//						csQna.id,
//						csQna.title,
//						csQna.userId,
//						csQna.upDate,
//						csQna.checked,
//						csQna.conId)
//				)
//				.from(csQna)
//				.where(csQna.id.eq(csQna.conId))
//				.orderBy(csQna.id.desc())
//				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
//				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
//				.fetch();
		
		return null;
	}
	
	
	
	
	//경고게시판
//	public BooleanExpression warnRepoterLike(String searchQuery) {
//		return StringUtils.isEmpty(searchQuery) ? null : QWarnBoard.warnBoard.reportedId.like("%" + searchQuery + "%");
//	}
	
	//경고 게시판 리스트 불러오기
	@Override
	public Page<WarnBoardDto> getWarnList(UserSearchDto userSearchDto, WarnBoardDto warnBoardDto, Pageable pageable) {
		QWarnBoard warnBoard = QWarnBoard.warnBoard;
		
		List<WarnBoardDto> content = queryFactory.select(
				Projections.constructor(WarnBoardDto.class,
						warnBoard.id,
						warnBoard.reportedId,
						warnBoard.reporterId,
						warnBoard.warnType,
						warnBoard.description,
						warnBoard.boardType,
						warnBoard.boardId,
						warnBoard.regDate
						)
				)
				.from(warnBoard)
				.orderBy(warnBoard.id.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(warnBoard)
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}

}
