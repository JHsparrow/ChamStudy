package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.UserInfo;

public interface UserMainMypageRepository extends JpaRepository<UserInfo, Long>{

	UserInfo findByEmail(String email);
}
