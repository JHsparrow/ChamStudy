package ChamStudy.Service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Entity.User;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService{
	private final UserRepository userRepository;

	//이메일 중복 체크 실행 및 레파지토리에 저장
	public User saveUser(User user) {
		validateDuplicateUser(user); //중복되면 여기서 걸림
		return userRepository.save(user); //중복이 없으면 세이브
	}
	
	//이메일 중복 체크
	private void validateDuplicateUser(User user) {
		User findUser = userRepository.findByEmail(user.getEmail());
		if(findUser != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
}
