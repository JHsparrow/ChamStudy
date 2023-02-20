package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>{
	UserInfo findByemail(String email);
}
