package ChamStudy.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.AdminApplyClassInterface;
import ChamStudy.Dto.AdminApplyClassListDto;
import ChamStudy.Dto.AdminApplySubListDto;
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
		
		if (userInfo == null) {
			return (long) -7;
		}
		
		ApplyList savedClass = applyListRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
        if(savedClass != null){
        	return (long) -777;
        } else {
        	ApplyList applyListAdd = ApplyList.createApplyList(classInfo, userInfo);
        	applyListRepository.save(applyListAdd);
            return applyListAdd.getId();
        }
	}
	
	//수강신청 조회
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
	
//	public Page<AdminApplyClassListDto> getAdminApplyList(AdminApplyClassListDto adminApplyListDto, Pageable pageable){
//		return adminApplyRepository.getAdminApplyList(adminApplyListDto , pageable);
//	}
	
	public List<AdminApplyClassInterface> getAdminApplyClassList(){
		return adminApplyRepository.getAdminApplyList();
	}
	
	public Page<AdminApplySubListDto> getAdminApplyList(AdminApplySubListDto adminApplySubListDto, Pageable pageable,UserSearchDto userSearchDto,Long classId){
		return adminApplyRepository.getAdminApplySubList(adminApplySubListDto , pageable, userSearchDto, classId);
	}
	
}
