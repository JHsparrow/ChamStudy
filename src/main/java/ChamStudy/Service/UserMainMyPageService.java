package ChamStudy.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.CompletionRepository;
import ChamStudy.Repository.UserMainMypageRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMainMyPageService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserMainMypageRepository userMainMypageRepository;
	private final UserRepository userRepository;
	private final CompletionRepository completionRepository;
	
	public UserInfoDto getUser(String email) throws Exception{
		
		UserInfo userInfo = userMainMypageRepository.findByEmail(email);
		
		UserInfoDto userInfoDto = new UserInfoDto();
		
		userInfoDto.setEmail(userInfo.getEmail());
		userInfoDto.setGubun(userInfo.getGubun());
		userInfoDto.setName(userInfo.getName());
		userInfoDto.setPhone(userInfo.getPhone());
		userInfoDto.setPassword(userInfo.getPassword());
		userInfoDto.setRegTime(userInfo.getRegDate());
		userInfoDto.setRole(userInfo.getRole());
		
		
		return userInfoDto;
	}
	
	public Long getUserId(String email) {
		Long userId = userMainMypageRepository.getUserId(email);
		return userId;
	}
	
	public Long updateUser(UserInfoDto userInfoDto) throws Exception{
		
		UserInfo userInfo = userRepository.findByemail(userInfoDto.getEmail());
		
		userInfo.updateUserMypage(userInfoDto, passwordEncoder);
		
		return userInfo.getId();
	
}

	public String getCategoryName() {
		String cateName = completionRepository.getCategoryName();
		return cateName;
	}
	
	@Transactional(readOnly = true)
	public Page<CompletionListDto> getCompletionList(UserSearchDto userSearchDto, CompletionListDto completionListDto, Pageable pageable, Long id){
		return completionRepository.getCompletionList(userSearchDto, completionListDto, pageable, id);
	}
	
}