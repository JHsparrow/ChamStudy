package ChamStudy.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Dto.AdminApplyClassInterface;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Impl.AdminApplyCustom;

public interface AdminApplyRepository extends JpaRepository<ApplyList, Long>,QuerydslPredicateExecutor<ApplyList>, AdminApplyCustom {
	
	@Query(value="select a.class_id as id,a.class_name as name, a.s_date as sdate, a.e_date as edate,  count(b.apply_id) as count , sum(c.progress) as progress"
			+ " from class_info A "
			+ " join apply_list B on a.class_id = b.class_id "
			+ " left join study_result C on b.apply_id = c.apply_id "
			+ " group by a.class_id ", nativeQuery = true)
	List<AdminApplyClassInterface> getAdminApplyList();
}
