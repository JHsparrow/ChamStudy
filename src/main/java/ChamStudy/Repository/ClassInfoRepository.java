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
	
<<<<<<< HEAD
=======
	@Query(value="select A.class_name as className, "
			+ " (select count(*)*20/count(A.class_id) from study_history where content_id = a.content_id ) as progressRate, "
			+ " (select count(*)*100 / (select count(*) from apply_list where class_id = a.class_id) from apply_list where com_flag='Y' and class_id = a.class_id) as completionRate "
			+ " from class_info A "
			+ " join apply_list B on a.class_id = b.class_id "
			+ " group by A.class_id", nativeQuery = true)
	List<EducationInfoInterface> educationInfo();
	
	
>>>>>>> 02172895da323f79c2e2bd92cc791deb16e1b9f0
}