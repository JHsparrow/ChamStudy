package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query(value="select * from category ",nativeQuery = true)
	List<Category> findAllList();
}
