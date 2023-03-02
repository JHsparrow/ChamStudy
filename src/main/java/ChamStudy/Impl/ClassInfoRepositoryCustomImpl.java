package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
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
                		classInfo.name, classInfo.price, classInfo.peopleNum, classInfo.regDate.as("date"), classInfo.teacherName, 
                		classInfo.sDate, classInfo.eDate, contentInfo.id, contentInfo.name, contentInfo.imgUrl)) //select 컬럼1, 컬럼2, 컬럼... from class_info
                .from(classInfo)
                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //조회된 결과 list 객체 로 받을 때 사용
		
        long total = queryFactory
        		.select(Wildcard.count) //select count(*)
        		.from(classInfo) //from class_info
        		.join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .fetchOne(); //조회된 결과 단일 객체로 받을 때 사용

        return new PageImpl<>(classInfoList, pageable, total);
	}
	
	@Override
	public Page<ClassInfoListDto> findByClassList(ClassInfoDto adminClassDto, Pageable pageable) {
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		
		List<ClassInfoListDto> classList = queryFactory		
                .select(Projections.constructor(ClassInfoListDto.class, classInfo.id,
                		classInfo.name, classInfo.price, classInfo.peopleNum, classInfo.regDate.as("date"), classInfo.teacherName, 
                		classInfo.sDate, classInfo.eDate, contentInfo.id, contentInfo.name, contentInfo.imgUrl)) //select 컬럼1, 컬럼2, 컬럼... from class_info
                .from(classInfo)
                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .where(
                		classInfo.contentInfo.name.contains(adminClassDto.getName()) //like %name%
                		.or(classInfo.name.like("%"+adminClassDto.getName()+"%")) //like %name%
                		)
                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //조회된 결과 list 객체 로 받을 때 사용
		
		//classInfo.contentInfo.name.like("검색어%") //like ‘검색어%’
		//classInfo.contentInfo.name.contains("검색어") // like ‘%검색어%’
		//classInfo.contentInfo.name.startsWith("검색어") //like ‘검색어%’
		
        long total = queryFactory
        		.select(Wildcard.count) //select count(*)
        		.from(classInfo)
                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .where(classInfo.contentInfo.name.eq(adminClassDto.getName()))
                .fetchOne(); //조회된 결과 단일 객체로 받을 때 사용
		
		return new PageImpl<>(classList, pageable, total);
	}

	@Override
	public ClassInfoListDto findByClassDetail(ClassInfoDto adminClassDto) {
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		
		ClassInfoListDto classDetail = queryFactory		
                .select(Projections.constructor(ClassInfoListDto.class, classInfo.id,
                		classInfo.name, classInfo.price, classInfo.peopleNum, classInfo.regDate.as("date"), classInfo.teacherName, 
                		classInfo.sDate, classInfo.eDate, contentInfo.id, contentInfo.name, contentInfo.imgUrl)) //select 컬럼1, 컬럼2, 컬럼... from class_info
                .from(classInfo)
                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
                .where(classInfo.id.eq(adminClassDto.getId()))
                .fetchOne(); //조회된 결과 단일 객체로 받을 때 사용
		
		return classDetail;
	}
	
	@Override
	public long updateByClassDetail(ClassInfoDto adminClassDto) {
		QClassInfo classInfo = QClassInfo.classInfo;
		QContentInfo contentInfo = QContentInfo.contentInfo;
		
		long classDetail = queryFactory		
                .update(classInfo) //update class_info
                .set(classInfo.name, adminClassDto.getName())
                .set(classInfo.teacherName, adminClassDto.getTeacherName())
                .set(classInfo.contentInfo.id, adminClassDto.getContentId())
                .set(classInfo.peopleNum, adminClassDto.getPeopleNum())
                //.set(classInfo.regDate, adminClassDto.getRegDate())
                .set(classInfo.sDate, adminClassDto.getSDate())
                .set(classInfo.eDate, adminClassDto.getEDate())
                .set(classInfo.price, adminClassDto.getPrice())
                .where(classInfo.id.eq(adminClassDto.getId()))
                .execute(); //update 실행
		
		return classDetail;
	}
}
