package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Entity.StudyResult;

public interface StudyResultRepository extends JpaRepository<StudyResult, Long> {
	
	@Query(value="select * from study_result where apply_id = ?1", nativeQuery = true)
	StudyResult getResultId(Long applyId);
}
