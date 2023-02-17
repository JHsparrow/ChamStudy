package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.Comm_Board_Img;

public interface CommImgRepository extends JpaRepository<Comm_Board_Img, Integer>{
	//메인에 뿌려줄 데이터를 위해 작성. 아직은 미완성으로 지금은 모든 게시판을 가져온다.
	//완성은 메소드 3개를 따로 작성해 자유/멘토멘티/QnA게시판을 따로 가져와야한다.
	List<Comm_Board_Img> findByBoardIdOrderByIdAsc(Integer BoardId);
}
