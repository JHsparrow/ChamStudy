package ChamStudy.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

//구글 후처리 해주는 서비스
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	@Autowired
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	
	
	//구글로 부터 받은 userrequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:" + userRequest.getClientRegistration()); //어떤 OAuth로 로그인 했는지 확인 가능
		System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue()); //토큰 정보 확인가능, 지금은 필요없음
		System.out.println("getClientRegistration:" + userRequest.getClientRegistration().getClientId());
		// 구글로가인 버튼 클릭 -> 구글 로그인 창-> 로그인 완료 -> code를 리턴(Oauth-cient라이브러리가 받아줌) -> AccessToken 요청
		//userRequest 정보를 받고 loadUser 함수를 호출 후 구글로 부터 회원 프로필을 받아줌
		System.out.println("getAttributes:" + super.loadUser(userRequest).getAttributes());
//===========================================================================================================================
		OAuth2User oauth2User = super.loadUser(userRequest);
		Random rand = new Random();
		String temp = Integer.toString( rand.nextInt(8) + 1);
		for (int i = 0; i < 7; i++) {
		    temp+= Integer.toString(rand.nextInt(9));
		}
		String password1 = passwordEncoder.encode(temp);
		
		
		LocalDateTime localDateTime = LocalDateTime.now();
		String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
		System.out.println(time);

		
		String email = oauth2User.getAttribute("email");
		String username = oauth2User.getAttribute("name");
		String password = password1;
		String role = "USER";
		String regDate = time;
		String phone = "0";
		String gubun = "G";
		
		UserInfo userEntity =  userRepository.findByemail(email);
		
		UserInfoDto userInfo = new UserInfoDto();
		userInfo.setEmail(email);
		userInfo.setName(username);
		userInfo.setPassword(password);
		userInfo.setPhone(phone);
		userInfo.setRole(role);
		userInfo.setRegTime(regDate);
		userInfo.setGubun(gubun);
		
		
		try {
			UserInfo user = UserInfo.createUser(userInfo, passwordEncoder);
			userService.saveUser(user);
		} catch (Exception e) {
			System.out.println("구글 트라이문 오류");
		}
		
		if(userEntity == null) {
			userEntity = UserInfo.builder()
					.name(email)
					.password(password)
					.role(role)
					.build();
		}else {
			
		}
		
		
		
			
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
	

}
