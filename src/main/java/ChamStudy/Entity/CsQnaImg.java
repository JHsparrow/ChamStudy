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
import lombok.ToString;

@Entity
@Table(name="cs_qna_img")
@Getter
@Setter
@ToString
@SequenceGenerator(
		name="QNAIMG_GEN_GEN",	//1:1문의 이미지 시퀀스
		sequenceName="QNA_IMG_SEQ",	//시퀀스 이름
		initialValue=100	//시작값
		)
public class CsQnaImg {
	
	@Id
	@Column(name = "qna_img_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "QNAIMG_GEN_GEN")
	private Long id;
	
	private String oriImgName;
	
	private String imgName;
	
	private String imgUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qna_id")
	private CsQna csQna;
	
	public void updateCsQnaImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
