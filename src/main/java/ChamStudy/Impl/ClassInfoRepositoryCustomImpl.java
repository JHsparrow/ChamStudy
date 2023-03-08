package ChamStudy.Impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QClassInfo;
import ChamStudy.Entity.QContentInfo;
import ChamStudy.Entity.QSubCategory;

public class ClassInfoRepositoryCustomImpl implements ClassInfoRepositoryCustom {
	private JPAQueryFactory queryFactory;
	
    public ClassInfoRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    //서브카테고리 필터
    public BooleanExpression subCategoryLike(String searchQuery) {
    	if(searchQuery ==  null || searchQuery.equals("all")) {
    		return null;
    	} else {
    		return QSubCategory.subCategory.name.like("%" + searchQuery + "%");
    	}
    }	

    /**
     * class_name, content_name 검색 필터
     * @param searchText
     * @return
     */
    public BooleanExpression nameLike(String searchText) {
    	if(searchText ==  null || searchText.equals("")) {
    		return null;
    	} else {
    		String likeText = "%" + searchText + "%";
    		return QClassInfo.classInfo.name.like( likeText ).or(QContentInfo.contentInfo.name.like( likeText ));
    		
    		//위와 동일하게 작동한다 contains = like %<검색어>%
    		//String likeText = searchText;
    		//return QClassInfo.classInfo.name.contains( likeText ).or(QContentInfo.contentInfo.name.contains( likeText ));
    	}
    }

	@Override
	public Page<ClassInfoListDto> joinContent(UserSearchDto userSearchDto, Pageable pageable) {
			QClassInfo classInfo = QClassInfo.classInfo;
			QContentInfo contentInfo = QContentInfo.contentInfo;
			QSubCategory subCategory = QSubCategory.subCategory;
			
			List<ClassInfoListDto> classInfoList = queryFactory
	                .select(Projections.constructor(ClassInfoListDto.class, classInfo.id,
	                		classInfo.name, classInfo.price, classInfo.peopleNum, classInfo.regDate.as("date"), classInfo.teacherName, 
	                		classInfo.sDate, classInfo.eDate, contentInfo.id, contentInfo.name, contentInfo.imgUrl)) //select 컬럼1, 컬럼2, 컬럼... from class_info
	                .from(classInfo)
	                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
	                .join(subCategory).on(contentInfo.subCategoryId.id.eq(subCategory.id))
	                .where(
	                		subCategoryLike(userSearchDto.getSearchQuery()),
	                		nameLike(userSearchDto.getSearchText())
	                		)
	                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
	                .offset(pageable.getOffset())
	                .limit(pageable.getPageSize())
	                .fetch(); //조회된 결과 list 객체 로 받을 때 사용
			
			//위에 수정 수 total 쪽도 반드시 수정해 주어야 전체 페이지 수를 정확하게 가져온다
	        long total = queryFactory
	        		.select(Wildcard.count) //select count(*)
	        		.from(classInfo)
	                .join(contentInfo).on(classInfo.contentInfo.id.eq(contentInfo.id))
	                .join(subCategory).on(contentInfo.subCategoryId.id.eq(subCategory.id))
	                .where(
	                		subCategoryLike(userSearchDto.getSearchQuery()),
	                		nameLike(userSearchDto.getSearchText())
	                		)
	                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
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
                		classInfo.contentInfo.name.like("%"+adminClassDto.getName()+"%")
                		.or(classInfo.name.like("%"+adminClassDto.getName()+"%"))
                		
                		//위와 동일하게 작동한다 contains = like %<검색어>%
                		//classInfo.contentInfo.name.contains(adminClassDto.getName())
                		//.or(classInfo.name.contains(adminClassDto.getName()))
                		)
                .orderBy(QClassInfo.classInfo.id.desc()) //order by class_id desc
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //조회된 결과 list 객체 로 받을 때 사용
		
		//classInfo.contentInfo.name.like("검색어%") //like ‘검색어%’
		//classInfo.contentInfo.name.contains("검색어") // like ‘%검색어%’
		//classInfo.contentInfo.name.startsWith("검색어") //like ‘검색어%’
		
		//위에 수정 수 total 쪽도 반드시 수정해 주어야 전체 페이지 수를 정확하게 가져온다
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
