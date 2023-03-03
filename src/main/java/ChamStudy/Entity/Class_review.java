package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="class_review") // 강의 리뷰
@Getter
@Setter
@ToString
public class Class_review {
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userId;
	
	private String description;
	
	private Integer starPoint;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassInfo classId;
	
}
