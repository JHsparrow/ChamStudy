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
@SequenceGenerator(
        name="CLASS_GEN_GEN", //기수제 시퀸스 
        sequenceName="CLASS_SEQ", //시퀀스 이름
        initialValue=1000 //시작값
        )
@Table(name="class_info") // 클래스 정보
@Getter
@Setter
@ToString
public class ClassInfo {
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;	
	
	@Column(name="class_name")
	private String name;	
	
	private int price;
	
	private String sdate;
	
	private String edate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private ContentInfo contentId;
}