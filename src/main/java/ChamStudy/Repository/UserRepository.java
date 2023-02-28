package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Impl.UserRepositoryCustom;

public interface UserRepository extends JpaRepository<UserInfo, Long>,
QuerydslPredicateExecutor<UserInfo>, UserRepositoryCustom{
	
	UserInfo findByemail(String email);
	
	@Query(value="select * from user_info where user_email = ?1 ",nativeQuery = true)
	UserInfo getUserId(String email);
	
	@Query(value = "select * from user_info where gubun = 'N'", nativeQuery = true)
	String getGubun();
}
