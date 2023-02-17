package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.Comm_Board_Img;

public interface CommImgRepository extends JpaRepository<Comm_Board_Img, Integer>{
	List<Comm_Board_Img> findByBoardIdOrderByIdAsc(Integer BoardId);
}
