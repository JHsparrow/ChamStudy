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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
@Table(name="completion") // 수료테이블
@Getter
@Setter
@ToString
public class Completion {
	@Id
	@Column(name="completion_id")
	private Long id;  
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "result_id")
	private StudyResult resultId ;  
	
	
	@CreatedDate 
	@Column(updatable = false)
	private String regDate; //수료일
}
