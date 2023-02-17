package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	User findByEmail(String email);
}
