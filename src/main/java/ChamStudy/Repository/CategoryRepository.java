package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//	@Query(value="select * from category ",nativeQuery = true)
	@Query(value="select A.category_id as id,A.name as name, A.reg_date as date, case when A.category_id is not null then count(B.sub_category_id) end as count from category A left join sub_category B on a.category_id = b.category_id ",nativeQuery = true)
	Page<CategoryInterface> findAllList(Pageable pageable);
	
	@Query(value="select * from category where category_id = ?1 ",nativeQuery = true)
	Category findMainInfo(Long mainId);
	
}
