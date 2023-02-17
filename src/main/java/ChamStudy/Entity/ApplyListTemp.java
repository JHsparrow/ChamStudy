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
@Table(name="apply_list_temp") // 임시 강의 리스트 
@Getter
@Setter
@ToString
public class ApplyListTemp{
	@Id
	@Column(name="apply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserInfo userId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ClassInfo classId;
	
	@CreatedDate 
	@Column(updatable = false)
	private String regDate;
	
}
