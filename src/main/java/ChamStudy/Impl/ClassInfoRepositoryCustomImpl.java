package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.QClassInfo;

public class ClassInfoRepositoryCustomImpl implements ClassInfoRepositoryCustom {
	private JPAQueryFactory queryFactory;
	
    public ClassInfoRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    
	@Override
	public Page<ClassInfo> findAll(Pageable pageable) {
		List<ClassInfo> classInfo = queryFactory
                .selectFrom(QClassInfo.classInfo) //select 컬럼1, 컬럼2, 컬럼... from class_info
                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
        		.select(Wildcard.count) //select count(*)
        		.from(QClassInfo.classInfo) //from class_info
                .fetchOne();

        return new PageImpl<>(classInfo, pageable, total);
	}

}
