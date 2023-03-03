package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.CsQna;
import ChamStudy.Impl.CsRepositoryCustom;

public interface CsQnaRepository extends JpaRepository<CsQna, Long>,
	QuerydslPredicateExecutor<CsQna>, CsRepositoryCustom {
	
//	@Query(value = "select qna_id from cs_qna order by qna_id desc limit 1", nativeQuery = true)
//	Long getConId();
	
	@Query(value = "select * from cs_qna order by qna_id desc limit 1", nativeQuery = true)
	CsQna getQna();
	
}
