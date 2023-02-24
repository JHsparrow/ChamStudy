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

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Dto.QAdminMainCommDto;
import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.QComm_Board;
import ChamStudy.Entity.QComm_Board_Img;

public class CommRepositoryCustomImpl implements CommRepositoryCustom{

	/** 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용합니다. */
    private JPAQueryFactory queryFactory;
	
    /** JPAQueryFactory 생성자로 EntityManager 객체를 넣어줍니다.   */
    public CommRepositoryCustomImpl(EntityManager em){
        this.queryFactory =new JPAQueryFactory(em);
    }
    
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
    	//내용으로 검색하기
    	if(StringUtils.equals("substance", searchBy)){
            return QComm_Board.comm_Board.substance.like("%" + searchQuery + "%");
        }else if (StringUtils.equals("name", searchBy)){
            return QComm_Board.comm_Board.userId.name.like("%"+searchQuery+"%");
        }else if (StringUtils.equals("Title", searchBy)){
            return QComm_Board.comm_Board.Title.like("%"+searchQuery+"%");
        }
        return  null;
    }
    
	@Override
	public Page<Comm_Board> getAdminComm(CommSearchDto commSearchDto, Pageable pageable) {
		List<Comm_Board> content = queryFactory
				.selectFrom(QComm_Board.comm_Board) //select * from item
				.where(searchByLike(commSearchDto.getSearchBy(), commSearchDto.getSearchQuery())) // and itemNm LIKE %검색어%
				.orderBy(QComm_Board.comm_Board.regDate.desc())
				.offset(pageable.getOffset()) //데이터를 가져올 시작 index
				.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수
				.fetch();
		
		//https://querydsl.com/static/querydsl/4.1.0/apidocs/com/querydsl/core/types/dsl/Wildcard.html
		// Wildcard.count = count(*)
		long total = queryFactory.select(Wildcard.count).from(QComm_Board.comm_Board)
                .where(searchByLike(commSearchDto.getSearchBy(), commSearchDto.getSearchQuery()))
                .fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}
	private BooleanExpression commNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QComm_Board.comm_Board.userId.name.like("%" + searchQuery + "%");
    }
	
	@Override
	public Page<AdminMainCommDto> getAdminCommDto(CommSearchDto commSearchDto, Pageable pageable) {
		QComm_Board comm = QComm_Board.comm_Board;
        QComm_Board_Img comm_Board_Img = QComm_Board_Img.comm_Board_Img;

        List<AdminMainCommDto> content = queryFactory
                .select(
                        new QAdminMainCommDto(comm)
                )
                .from(comm_Board_Img)
                .join(comm_Board_Img.board, comm)
                .where(commNmLike(commSearchDto.getSearchQuery()))
                .orderBy(comm.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(comm_Board_Img)
                .join(comm_Board_Img.board, comm)
                .where(commNmLike(commSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
	}
	
	
}
