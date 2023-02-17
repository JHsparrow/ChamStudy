package ChamStudy.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_info")
@Getter
@Setter
@ToString
public class User_info { //회원 테이블
	
	@Id
	@Column(name = "email")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String email; //이메일
	
	private String userName; //회원 이름
	
	private String password; //회원 비밀번호
	
	private String phone; //핸드폰
	
	private String role; //구분
	
	private Date reg_date; //가입일
}
