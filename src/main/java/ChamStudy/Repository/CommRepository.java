package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.Comm_Board;
import ChamStudy.Impl.CommRepositoryCustom;

public interface CommRepository extends JpaRepository<Comm_Board, Long> , QuerydslPredicateExecutor<Comm_Board>, CommRepositoryCustom{
	//메인에 뿌려줄 데이터를 위해 작성. 아직은 미완성으로 지금은 모든 게시판을 가져온다.
	//완성은 메소드 3개를 따로 작성해 자유/멘토멘티/QnA게시판을 따로 가져와야한다.
	
	//F = freeboard 자유게시판을 최신순으로 불러와준다.
	@Query(value = "SELECT * FROM comm_board c WHERE c.board_type = 'F' and c.gubun = 'B' order by c.reg_date desc;",nativeQuery = true)
	List<Comm_Board> findF();
	
	//Q = QnAboard 질의응답 게시판을 최신순으로 불러와준다.
	@Query(value = "SELECT * FROM comm_board c WHERE c.board_type = 'Q' and c.gubun = 'B' order by c.reg_date desc;",nativeQuery = true)
	List<Comm_Board> findQ();
	
	//M = Mento 멘토게시판을 최신순으로 불러와준다.
	@Query(value = "SELECT * FROM comm_board c WHERE c.board_type = 'M' and c.gubun = 'B' order by c.reg_date desc;",nativeQuery = true)
	List<Comm_Board> findM();
	
	//게시글과 함께 해당 게시글의 댓글까지 가져온다.
	@Query(value = "SELECT * FROM comm_board c WHERE c.board_id - :boardId > 0 and c.board_id - :boardId < 1000000 and gubun = 'C';",nativeQuery = true)
	List<Comm_Board> findComment(@Param("boardId") Long boardId);

	@Query(value = "SELECT * FROM comm_board c WHERE c.board_id - :boardId > 0 and c.board_id - :boardId < 1000000 and gubun = 'R';",nativeQuery = true)
	List<Comm_Board> findreply(@Param("boardId") Long boardId);

	
	
}
