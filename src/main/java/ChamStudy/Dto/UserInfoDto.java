package ChamStudy.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {

	@NotBlank(message="아이디은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String id;
	
	@NotEmpty(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해 주세요.")
	private String password;
	
	@NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다.")
	private String phone;
	
	private String role;
	
	private String regTime;
}
