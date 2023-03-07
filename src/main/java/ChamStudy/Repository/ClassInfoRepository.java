package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.EducationInfoInterface;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Impl.ClassInfoRepositoryCustom;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>
										, QuerydslPredicateExecutor<ClassInfo>
										, ClassInfoRepositoryCustom {
	List<ClassInfo> findAll();
	
	Optional<ClassInfo> findById(Long id); //강의리스트 삭제를 위한 아이디조회
	
	
	@Query(value="select A.class_name as className,a.class_id as id ,d.name as subname, \r\n"
			+ "(select count(*)*20/count(A.class_id) from study_history where content_id = a.content_id ) as progressRate,\r\n"
			+ "(select count(*)*100 / (select count(*) from apply_list where class_id = a.class_id) from apply_list where com_flag='Y' and class_id = a.class_id) as completionRate \r\n"
			+ "from class_info A \r\n"
			+ "join apply_list B on a.class_id = b.class_id \r\n"
			+ "join content_info C on a.content_id = c.content_id\r\n"
			+ "join sub_category D on d.sub_category_id = c.sub_category_id\r\n"
			+ "group by A.class_id;", nativeQuery = true)
	List<EducationInfoInterface> educationInfo();
	
	@Query(value="select * from class_info where content_id = ?1", nativeQuery = true)
	ClassInfo getClassInfo(Long contentId );
	
	@Query(value="select * from class_info where content_id = ?1 and class_id = ?2", nativeQuery = true)
	ClassInfo getClassInfoConClass(Long contentId, Long classId );

}