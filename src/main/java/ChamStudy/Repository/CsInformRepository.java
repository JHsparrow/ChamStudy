package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.CsInform;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Impl.CsRepositoryCustom;

public interface CsInformRepository extends JpaRepository<CsInform, Long>,
	QuerydslPredicateExecutor<CsInform>, CsRepositoryCustom {
	
	@Query(value = "select count(*) from cs_inform where gubun = 'f'", nativeQuery = true)
	int findByGubun();
	
//	@Query(value = "select * from user_info i where i.user_email = :email", nativeQuery = true)
//	UserInfo getUserId(String email);
	
	UserInfo findByUserId(String email);
	
	/*
	 * @Query(value = "select * from cs_inform_file where inform_id = ?1",
	 * nativeQuery = true) List<CsInformFile> findByInformIdOrderByIdAsc(Long
	 * informId);
	 */
}
