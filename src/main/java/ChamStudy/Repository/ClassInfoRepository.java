package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.EducationInfoDto;
import ChamStudy.Dto.EducationInfoInterface;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Impl.ClassInfoRepositoryCustom;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>
										, QuerydslPredicateExecutor<ClassInfo>
										, ClassInfoRepositoryCustom {
	List<ClassInfo> findAll();
	
	Optional<ClassInfo> findById(Long id); //강의리스트 삭제를 위한 아이디조회
	
	@Query("SELECT c.className, " +
		       "COUNT(*)*20/COUNT(c.classId) AS progressRate, " +
		       "COUNT(*)*100/(SELECT COUNT(*) FROM ApplyList al WHERE al.classId = c.classId AND al.comFlag = 'Y') AS completionRate " +
		       "FROM ClassInfo c " +
		       "JOIN ApplyList al ON c.classId = al.classId " +
		       "GROUP BY c.classId")
	List<EducationInfoDto> educationInfo();
	
	
}