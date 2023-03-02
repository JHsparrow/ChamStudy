package ChamStudy.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
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
public class UserInfo implements UserDetails {
	
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm:ss")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
    }
	
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
		
		UserInfo user = new UserInfo();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPhone(userDto.getPhone());
		
		//패스워드 암호화
		String password = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(password);
		
		user.setRole(role);
		
		if(userDto.getPhone().equals("0")) {
			user.setGubun("G");
		} else {
			user.setGubun("N");
		}
		
		
		return user;
	}

	
	public static String passwordEn (String tmep, PasswordEncoder passwordEncoder) {
		
		String password = passwordEncoder.encode(tmep);
		
		return password;
	}
	
	//업데이트
	public void updateUser(UserListDto userListDto) {
		this.id = userListDto.getId();
		this.email = userListDto.getEmail();
		this.name = userListDto.getName();
		this.password = userListDto.getPassword();
		this.phone = userListDto.getPhone();
		this.role = userListDto.getRole();
		this.regDate = userListDto.getRegDate();
		this.gubun = userListDto.getGubun();
	}
	
	//업데이트(mypage)
	public void updateUserMypage(UserInfoDto userInfoDto) {
		this.email = userInfoDto.getEmail();
		this.name = userInfoDto.getName();
		this.password = userInfoDto.getPassword();
		this.phone = userInfoDto.getPhone();
		this.role = userInfoDto.getRole();
		this.regDate = userInfoDto.getRegTime();
		this.gubun = userInfoDto.getGubun();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(role.toString()));
		return auth;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getUname() {
		return name;
	}


	
	
	
}
