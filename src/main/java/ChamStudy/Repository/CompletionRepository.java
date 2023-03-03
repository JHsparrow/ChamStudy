package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Entity.Completion;


public interface CompletionRepository extends JpaRepository<Completion, Long> {
	
	@Query(value="select * from completion where result_id = ?1", nativeQuery = true)
	Completion getCompletion(Long resultId); 

}
