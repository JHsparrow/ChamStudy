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

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
@Table(name="user_info") 
@Getter
@Setter
@SequenceGenerator(
        name="USER_GEN_GEN", //기수제 시퀸스 
        sequenceName="USER_SEQ", //시퀀스 이름
        initialValue=10001 //시작값
        )
@ToString
public class UserInfo {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="USER_GEN_GEN" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정        
            )
	private Long id;
	
	@Column(name="user_email")
	private String email;  //이메일
	
	@Column(name="user_name")
	private String name;  //회원이름
	
	private String password; //회원 비밀번호
	
	private String phone; //회원 전화번호
	
	private String role; //회원 등급	
	
	@CreatedDate 
	@Column(updatable = false)
	private String regDate; //회원 가입일
	
	@Column(columnDefinition = "CHAR", length=1) 
	private String gubun;

	
	public UserInfo(){}
	
	@Builder
	public UserInfo (String email, String name, String password, String phone, String role, String regDate, String gubun) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.regDate = regDate;
		this.gubun = gubun;
	}
	
	public static UserInfo createUser(UserInfoDto userDto, PasswordEncoder passwordEncoder) {
		String role = "USER";
		LocalDateTime localDateTime = LocalDateTime.now();
		String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
		
		
		
		UserInfo user = new UserInfo();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPhone(userDto.getPhone());
		
		//패스워드 암호화
		String password = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(password);
		
		user.setRole(role);
		
		user.setRegDate(time);
		
		user.setGubun("N");
		
		return user;
	}

	
	public static String passwordEn (String tmep, PasswordEncoder passwordEncoder) {
		
		String password = passwordEncoder.encode(tmep);
		
		return password;
	}
	
	


	
	
	
}
