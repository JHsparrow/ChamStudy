package ChamStudy.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.AdminApplyListDto;
import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.AdminApplyRepository;
import ChamStudy.Repository.ApplyListRepository;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyListService {
	
	private final ClassInfoRepository classInfoRepository;
	private final UserRepository userRepository;
	private final ApplyListRepository applyListRepository;
	private final AdminApplyRepository adminApplyRepository;
	
	
	public Long addClass(ApplyListDto applyListDto, String email) {
		//class id 에 해당하는 정보가 있는지 확인
		ClassInfo classInfo = classInfoRepository.findById(applyListDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		UserInfo userInfo = userRepository.findByemail(email);
		
		ApplyList savedClass = applyListRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
        if(savedClass != null){
            return savedClass.getId();
        } else {
        	ApplyList applyListAdd = ApplyList.createApplyList(classInfo, userInfo);
        	applyListRepository.save(applyListAdd);
            return applyListAdd.getId();
        }
	}
	
	public Long getApplyId(ApplyListDto applyListDto, String email) {
		//class id 에 해당하는 정보가 있는지 확인
		ClassInfo classInfo = classInfoRepository.findById(applyListDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		UserInfo userInfo = userRepository.findByemail(email);
		
		if (userInfo == null) {
			new EntityNotFoundException("userInfo is null.");
		}
		
		ApplyList getApplyClass = applyListRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
		Long applyId = (long) -1;
		
		if (getApplyClass == null) {
			applyId = (long) -1;
		} else {
			applyId = getApplyClass.getId();
		}
		
		return applyId;
	
	}
	
	public Page<AdminApplyListDto> getAdminApplyList(AdminApplyListDto adminApplyListDto, Pageable pageable,UserSearchDto userSearchDto){
		return adminApplyRepository.getAdminApplyList(adminApplyListDto , pageable, userSearchDto);
	}
	
}
