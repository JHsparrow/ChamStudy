package ChamStudy.Service;



import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.MainReviewDto;
import ChamStudy.Dto.MyPageYNumber;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;
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
		
		
		
		
		return userInfo;
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

	//유저 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<UserListDto> getUserPage(UserSearchDto userSearchDto,UserInfoDto userInfoDto,Pageable pageable) {
		return userRepository.getUserPage(userSearchDto, userInfoDto, pageable); 
	}

	//회원탈퇴
	public void deleteUser(Long id) {
		UserInfo userInfo = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		userRepository.delete(userInfo);
	}
	
	//회원정보 수정
	public Long updateUser(UserListDto userListDto) throws Exception{
		
		UserInfo userInfo = userRepository.findById(userListDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		userInfo.updateUser(userListDto);
		
		return userInfo.getId();
		
	}
	
	@Transactional(readOnly = true)
	public Long getYNumber(String email) {
		return userRepository.getYCount(email);
	}
	
	@Transactional(readOnly = true)
	public Long getNNumber(String email) {
		return userRepository.getNCount(email);
	}
	
	
	
}
