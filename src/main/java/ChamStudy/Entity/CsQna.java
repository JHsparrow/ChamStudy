package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ChamStudy.Dto.CsQnaDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cs_qna")
@Getter
@Setter
@ToString
@SequenceGenerator(
		name="QNA_GEN_GEN",	//자주묻는질문 시퀀스
		sequenceName="Qna_SEQ",	//시퀀스 이름
		initialValue=1000	//시작값
		)
public class CsQna extends BaseTimeEntity {
	
	@Id
	@Column (name = "qna_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "QNA_GEN_GEN")
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String substance;
	
	private Long conId;
	
	//답변여부
	@Column(nullable = false, columnDefinition = "CHAR", length = 1)
	private String checked;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userId;

	public void updateQna(CsQnaDto csQnaDto) {
		this.title = csQnaDto.getTitle();
		this.substance = csQnaDto.getSubstance();
	}
}
