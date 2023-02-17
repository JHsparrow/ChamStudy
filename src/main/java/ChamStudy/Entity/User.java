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
@Table(name="user") 
@Getter
@Setter
@ToString
public class User {
	@Id
	@Column(name="user_id")
	private String id;  //이메일
	
	@Column(name="user_name")
	private String name;  //회원이름
	
	private String password; //회원 비밀번호
	
	private String phone; //회원 전화번호
	
	private String role; //회원 등급	
	
	@CreatedDate 
	@Column(name="reg_date", updatable = false)
	private String regTime; //회원 가입일
}
