package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Impl.CategoryRepositoryCustom;

public interface CategoryRepository extends JpaRepository<Category, Long>,QuerydslPredicateExecutor<Category>, CategoryRepositoryCustom {
//	@Query(value="select A.category_id as id, A.name, A.reg_date as date, "
//			+" (select count(*) from sub_category where category_id = A.category_id) as count "
//			+" from category A \n#pageable\n "
//			,nativeQuery = true)
	
	@Query(value="select category_id as id, name, reg_date as date, "
			+" 2 as count "
			+" from category "
			,countQuery = "select count(*) from category " 
			,nativeQuery = true)
	Page<CategoryInterface> findAllList(Pageable pageable);
	
	@Query(value="select * from category where category_id = ?1 ",nativeQuery = true)
	Category findMainInfo(Long mainId);
	
}
