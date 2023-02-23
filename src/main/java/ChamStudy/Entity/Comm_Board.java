package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comm_board")  //자유게시판 테이블
@Getter
@Setter
@SequenceGenerator(
        name="COMM_GEN_GEN", //기수제 시퀸스 
        sequenceName="COMM_SEQ", //시퀀스 이름
        initialValue=1 //시작값
        )
public class Comm_Board extends BaseTimeEntity{ 
	//BaseEntity : 수정자와 등록자, 수정일 등록일 컬럼 추가 
	//커뮤니티 게시판
	
	@Id
	@Column(name = "board_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id; //게시판 아이디
	
	private String Title; //게시판 제목
	
	private String substance; //내용
	
	@Column(columnDefinition = "CHAR", length=1) 
	private String boardType; //게시글 종류
	
	@Column(columnDefinition = "CHAR", length=1) 
	private String blockComment; //차단 댓글 여부
	
	private String openChat; //오픈채팅 링크
	
	@Column(columnDefinition = "CHAR", length=1) 
	private String gubun; //구분 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userId; //작성자
	
	private int viewCount; //조회수
}
