package ChamStudy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String>{
	Optional<UserInfo> findById(String email);
}
