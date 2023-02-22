package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.CsInform;
import ChamStudy.Entity.CsInformFile;

public interface AdminCsFileRepository extends JpaRepository<CsInformFile, Long>{

}
