package ChamStudy.Impl;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Entity.QApplyList;
import ChamStudy.Entity.QApplySeq;
import ChamStudy.Entity.UserInfo;

public class ApplyListRepositoryCustomImpl implements ApplyListRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ApplyListRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Long saveApplyClass(ApplyListDto applyListDto, UserInfo session) {
		QApplySeq applySeq = QApplySeq.applySeq;
		QApplyList applyList = QApplyList.applyList;
		
		//현재 시퀀스 값 +1 조회
		long applyId = queryFactory
				.select(applySeq.nextVal)
				.from(applySeq)
				.fetchOne();
		
		//현재 시퀀스 값 +1 수정
		applyId++;
		
		long updCnt = queryFactory		
                .update(applySeq) //update apply_seq
                .set(applySeq.nextVal, applyId)
                .execute(); //update 실행
		
		
		//나의 강의실 insert
		long insCnt = queryFactory
				.insert(applyList)
				//.columns(applyList.id, applyList.comFlag, applyList.userInfo.id, applyList.classInfo.id, applyList.regDate)
			    //.values(applyId, "N", session.getId(), applyListDto.getClassId(), Expressions.dateTimeTemplate(LocalDateTime.class,"DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
			    //.values(applyId, "N", session.getId(), applyListDto.getClassId(), Expressions.stringTemplate("DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
				//.values(applyId, "N", session.getId(), applyListDto.getClassId(), "2023년 03월 03일")
				//.set(applyList.id, applyId)
				//.set(applyList.comFlag, "N")
				//.set(applyList.userInfo.id, session.getId())
				//.set(applyList.classInfo.id, applyListDto.getClassId())
				//.set(applyList.regDate, Expressions.stringTemplate("DATE_FORMAT(NOW(), '%Y년 %m월 %d일')"))
				//.execute()
				.set(applyList.id, 1000l)
				.set(applyList.comFlag, "N")
				.set(applyList.userInfo.id, 1001l)
				.set(applyList.classInfo.id, 1002l)
				.set(applyList.regDate, "2023년 03월 02일")
				.execute();
		
		return applyId;
	}
	
	
}
