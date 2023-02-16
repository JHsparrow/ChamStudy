package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comm_board")  //자유게시판 테이블
@Getter
@Setter
public class Comm_Board extends BaseTimeEntity{ 
	//BaseEntity : 수정자와 등록자, 수정일 등록일 컬럼 추가 
	//커뮤니티 게시판
	
	@Id
	@Column(name = "board_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; //게시판 아이디
	
	private String Title; //게시판 제목
	
	private String substance; //내용
	
	private int oriId; //작성글 아이디
	
<<<<<<< HEAD
	private String boardType; //게시글 종류
=======
	private String dType; //게시글 종류
>>>>>>> 2ff06f48b1324b01f39e9f647a4d8debff34ed0f
	
	private String gubun; //구분 
	
	@ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
	@JoinColumn(name = "email")
	private User_info user_info; //작성자
=======
	@JoinColumn(name = "user_id")
	private User userId; //작성자
>>>>>>> 2ff06f48b1324b01f39e9f647a4d8debff34ed0f
	
	private int viewCount; //조회수
}
