package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.CsInform;
import ChamStudy.Impl.CsRepositoryCustom;

public interface CsInformRepository extends JpaRepository<CsInform, Long>,
	QuerydslPredicateExecutor<CsInform>, CsRepositoryCustom {
	
	@Query(value = "select count(*) from cs_inform where gubun = 'f'", nativeQuery = true)
	int findByGubun();
	
	/*
	 * @Query(value = "select * from cs_inform_file where inform_id = ?1",
	 * nativeQuery = true) List<CsInformFile> findByInformIdOrderByIdAsc(Long
	 * informId);
	 */
}
