package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.CsInform;

public interface AdminCsRepository extends JpaRepository<CsInform, Long>, QuerydslPredicateExecutor<CsInform> {

}
