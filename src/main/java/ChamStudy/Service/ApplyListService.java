package ChamStudy.Service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.UserInfo;
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
	
	
	public Long addClass(ApplyListDto applyListDto, String email) {
		//class id 에 해당하는 정보가 있는지 확인
		ClassInfo classInfo = classInfoRepository.findById(applyListDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		UserInfo userInfo = userRepository.findByemail(email);
		
		//Long applyId = applyListRepository.saveApplyClass(applyListDto, session);
		//ApplyList applyList = ApplyList.createApplyList(classInfo, session);
		
		ApplyList applyList = applyListRepository.findByUserInfoId(userInfo.getId());
		
		if(applyList == null) {
			applyList = ApplyList.createApplyList(classInfo, userInfo);
			applyListRepository.save(applyList);
		}
		
		ApplyList savedClass = applyListRepository.findByClassInfoIdAndUserInfoId(classInfo.getId(), userInfo.getId());
		
        if(savedClass != null){
            return savedClass.getId();
        } else {
        	ApplyList applyListAdd = ApplyList.createApplyList(classInfo, userInfo);
        	applyListRepository.save(applyListAdd);
            return applyListAdd.getId();
        }
	}
}
