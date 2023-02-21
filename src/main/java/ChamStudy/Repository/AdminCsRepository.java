package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.CsInform;

public interface AdminCsRepository extends JpaRepository<CsInform, Long> {

}
