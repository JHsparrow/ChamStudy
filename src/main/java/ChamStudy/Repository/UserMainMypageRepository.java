package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Entity.UserInfo;

public interface UserMainMypageRepository extends JpaRepository<UserInfo, Long>{

	UserInfo findByEmail(String email);
	
	@Query(value="select user_id from user_info where user_email= ?1", nativeQuery = true)
	Long getUserId(String email);
}
