package ChamStudy.Service;



import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserInfo userInfo = userRepository.findByemail(email);
		
		if(userInfo == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(userInfo.getEmail())
				.password(userInfo.getPassword())
				.roles(userInfo.getRole())
				.build();
	}
	
	
	
	//이메일 중복 체크 실행 및 레파지토리에 저장
	public UserInfo saveUser(UserInfo user) {
		validateDuplicateUser(user); //중복되면 여기서 걸림
		return userRepository.save(user); //중복이 없으면 세이브
	}
	
	//이메일 중복 체크 
	private void validateDuplicateUser(UserInfo user) {
		UserInfo findUser = userRepository.findByemail(user.getEmail());
		if(findUser != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}


	
	
	
}
