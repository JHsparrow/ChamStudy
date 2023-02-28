package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

	@Query(value="select count(*) from Visitor" ,nativeQuery = true)
	Integer findCountVisitor();
	
}
