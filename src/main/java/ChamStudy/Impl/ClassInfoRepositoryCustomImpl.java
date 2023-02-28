package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QContentInfo;

public class ClassInfoRepositoryCustomImpl implements ClassInfoRepositoryCustom {
	private JPAQueryFactory queryFactory;
	
    public ClassInfoRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    
	@Override
	public Page<ClassInfoListDto> joinContent(Pageable pageable) {
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		
		List<ClassInfoListDto> classInfoList = queryFactory		
                .select(Projections.constructor(ClassInfoListDto.class, classInfo.id, 
                		classInfo.name, classInfo.price, classInfo.peopleNum, classInfo.teacherName, 
                		classInfo.sDate, classInfo.eDate, classInfo.regDate.as("date"), contentInfo.id, contentInfo.name)) //select 컬럼1, 컬럼2, 컬럼... from class_info
                .from(classInfo)
                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
        		.select(Wildcard.count) //select count(*)
        		.from(QClassInfo.classInfo) //from class_info
                .fetchOne();

        return new PageImpl<>(classInfoList, pageable, total);
	}

}
