package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>,
QuerydslPredicateExecutor<UserInfo>, UserRepositoryCustom{
	
	UserInfo findByemail(String email);
	
	
}
