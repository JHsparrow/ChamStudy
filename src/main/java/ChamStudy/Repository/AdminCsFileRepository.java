package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.CsInformFile;

public interface AdminCsFileRepository extends JpaRepository<CsInformFile, Long>{

	/* List<CsInformFile> findByInformIdOrderByIdAsc(Long informId); */
	
	@Query(value = "select * from cs_inform_file where inform_id = ?1", nativeQuery = true)
	List<CsInformFile> findByInformIdOrderByIdAsc(Long informId);
	
}
