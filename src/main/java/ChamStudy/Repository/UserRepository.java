package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Dto.MyPageYNumber;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Impl.UserRepositoryCustom;

public interface UserRepository extends JpaRepository<UserInfo, Long>,
QuerydslPredicateExecutor<UserInfo>, UserRepositoryCustom{
	
	UserInfo findByemail(String email);
	
	@Query(value="select * from user_info where user_email = ?1 ", nativeQuery = true)
	UserInfo getUserId(String email);
	
	@Query(value="select role from user_info where user_id =?1", nativeQuery = true)
	String getRole(Long id);
	
	@Query(value="select count(*) from apply_list A join user_info B on a.user_id = b.user_id where a.com_flag = 'Y' and b.user_email = ?1", nativeQuery = true)
	Long getYCount(String email);
	
	@Query(value="select count(*) from apply_list A join user_info B on a.user_id = b.user_id where a.com_flag = 'N' and b.user_email = ?1", nativeQuery = true)
	Long getNCount(String email);
	
}
