package ChamStudy.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import ChamStudy.Dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
@Table(name="user") 
@Getter
@Setter
@ToString
public class UserInfo {
	@Id
	@Column(name="user_id")
	private String id;  //이메일
	
	@Column(name="user_name")
	private String name;  //회원이름
	
	private String password; //회원 비밀번호
	
	private String phone; //회원 전화번호
	
	private String role; //회원 등급	
	
	@CreatedDate 
	@Column(updatable = false)
	private String regDate; //회원 가입일

	public static UserInfo createUser(UserDto userDto, PasswordEncoder passwordEncoder) {
		String role = "USER";
		LocalDateTime localDateTime = LocalDateTime.now();
		String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
		
		
		
		UserInfo user = new UserInfo();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setPhone(userDto.getPhone());
		
		//패스워드 암호화
		String password = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(password);
		
		user.setRole(role);
		
		user.setRegDate(time);
		
		return user;
	}
}
