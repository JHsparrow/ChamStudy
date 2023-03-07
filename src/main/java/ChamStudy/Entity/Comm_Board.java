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

import ChamStudy.Dto.CommMentoClassNameDto;
import ChamStudy.Dto.CommWriteFormDto;
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
	
	private Integer viewCount; //조회수
	
	private String className; //강의명 (멘토 페이지에서 사용)
	
	private Long oriId; //글 식별 아이디
	
	public void updateComm(CommWriteFormDto boardFormDto) {
		this.Title = boardFormDto.getTitle();
		this.substance = boardFormDto.getSubstance();
		this.openChat = boardFormDto.getOpenChat();
		
	}

	public void updateOri(Comm_Board board) {
		this.Title = board.getTitle();
		this.substance = board.getSubstance();
		this.openChat = board.getOpenChat();
		this.oriId = board.getOriId();
		
	}
	
	
}
