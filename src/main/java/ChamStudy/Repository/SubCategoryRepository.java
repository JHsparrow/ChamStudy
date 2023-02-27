package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.SubCategory;
import ChamStudy.Impl.SubCategoryRepositoryCustom;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>,QuerydslPredicateExecutor<SubCategory>, SubCategoryRepositoryCustom {
	@Query(value="select * from sub_category where category_id = ?1 ",nativeQuery = true)
	List<SubCategory> findSubCategoryMain(@Param("mainId") Long mainId);
	
	
}
