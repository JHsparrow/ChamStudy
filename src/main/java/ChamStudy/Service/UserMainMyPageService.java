package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.CompletionContentDto;
import ChamStudy.Dto.CompletionContentInterface;
import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.Completion;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.ApplyListRepository;
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
	private final ApplyListRepository applyListRepository;

	
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
	
	@Transactional(readOnly = true)
	public List<CompletionContentInterface> getVideo(Long contentId) {
		return completionRepository.getCompeltionContent(contentId);
	}
	
	@Transactional(readOnly = true)
	public CompletionContentInterface getVideoOne(Long contentId) {
		return completionRepository.getCompeltionContentOne(contentId);
	}
	
	@Transactional(readOnly = true)
	public CompletionContentInterface getVideoOther(Long contentId, Long videoId) {
		return completionRepository.getCompeltionContentOther(contentId, videoId);
	}
	
	@Transactional(readOnly = true)
	public Page<MyClassLearningDto> getLearningPage(MyClassLearningDto classLearningDto,Pageable pageable,MyClassLearningSearchDto classLearningSearchDto,String email){
		return applyListRepository.getLearningDto(classLearningDto, pageable,classLearningSearchDto,email);
	}
	
	@Transactional(readOnly = true)
	public CompletionContentInterface getLearningVideo1(Long contentId) {
		return completionRepository.getApplyContentOne(contentId);
	}
	
	@Transactional(readOnly = true)
	public List<CompletionContentInterface> getLearningVideo(Long contentId) {
		return completionRepository.getApplyContent(contentId);
	}
	
	@Transactional(readOnly = true)
	public CompletionContentInterface getLearningVideoOther(Long contentId, Long videoId) {
		return completionRepository.getLearningContentOther(contentId, videoId);
	}
	
	
	
}
