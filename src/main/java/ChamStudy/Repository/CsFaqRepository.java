package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.CsFaq;
import ChamStudy.Impl.CsRepositoryCustom;

public interface CsFaqRepository extends JpaRepository<CsFaq, Long>,
	QuerydslPredicateExecutor<CsFaq>, CsRepositoryCustom {
	
	
	
	/*
	 * @Query(value = "select * from cs_inform_file where inform_id = ?1",
	 * nativeQuery = true) List<CsInformFile> findByInformIdOrderByIdAsc(Long
	 * informId);
	 */
}
