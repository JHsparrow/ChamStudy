package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
@Table(name="study_result") // 강의 리뷰
@Getter
@Setter
@ToString
public class StudyResult extends BaseTimeEntity {
	@Id
	@Column(name="result_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_id")
	private ApplyList applyId;
	
	private int process;
	
	private int exam_score;
	
}
