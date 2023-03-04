package ChamStudy.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.ApplyList;
import ChamStudy.Impl.AdminApplyCustom;

public interface AdminApplyRepository extends JpaRepository<ApplyList, Long>,QuerydslPredicateExecutor<ApplyList>, AdminApplyCustom {
	
}
