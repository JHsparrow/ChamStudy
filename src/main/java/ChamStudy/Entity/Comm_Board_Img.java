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
@Table(name = "comm_board_img")
@Getter
@Setter
public class Comm_Board_Img extends BaseTimeEntity{ //게시판 이미지
	
	@Id
	@Column(name = "comm_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //게시판 이미지 식별자
	
	private String imgUrl; //이미지 링크
	
	private String name; //이미지 이름
	
	private String oriName; //이미지 원본 이름
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Comm_Board board; //게시판 식별자
	
	public void updateBoardImg(String oriName, String name, String imgUrl) {
		this.oriName = oriName;
		this.name = name;
		this.imgUrl = imgUrl;
}
	
}