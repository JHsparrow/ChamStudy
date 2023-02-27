package ChamStudy.Dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListDto {

	private Long id;
	
	private String email;  //이메일
	
	private String name;  //회원이름
	
	private String password; //회원 비밀번호
	
	private String phone; //회원 전화번호
	
	private String role; //회원 등급	
	
	private String regDate; //회원 가입일
	
	private String gubun;
	
	public UserListDto() {
	}
	
    @QueryProjection	//querydsl로 결과 조회 시 MainItemDto객체로 바로 받아올 수 있음
    public UserListDto(Long id, String email, String name, String password,String phone, String role, String regDate,String gubun){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.regDate = regDate;
        this.gubun = gubun;
    }
	
	
}
