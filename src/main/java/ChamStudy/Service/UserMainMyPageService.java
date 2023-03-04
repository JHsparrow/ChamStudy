package ChamStudy.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.CompletionRepository;
import ChamStudy.Repository.UserMainMypageRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMainMyPageService {

	private final UserMainMypageRepository userMainMypageRepository;
	private final CompletionRepository completionRepository;
	
	public UserInfoDto getUser(String email) throws Exception{
		
		UserInfo userInfo = userMainMypageRepository.findByEmail(email);
		
		UserInfoDto userInfoDto = new UserInfoDto();
		
		userInfoDto.setEmail(userInfo.getEmail());
		userInfoDto.setName(userInfo.getName());
		userInfoDto.setPhone(userInfo.getPhone());
		
		return userInfoDto;
	}
	
	public String getCategoryName() {
		String cateName = completionRepository.getCategoryName();
		return cateName;
	}
	
}
