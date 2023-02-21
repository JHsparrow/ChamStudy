package ChamStudy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
