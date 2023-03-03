package ChamStudy.Service;

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
	
	
	public Long addClass(ApplyListDto applyListDto, UserInfo session) {
		//class id 에 해당하는 정보가 있는지 확인
		ClassInfo classInfo = classInfoRepository.findById(applyListDto.getClassId())
				 .orElseThrow(EntityNotFoundException::new);
		
		Long applyId = applyListRepository.saveApplyClass(applyListDto, session);
		
		//ApplyList applyList = ApplyList.createApplyList(classInfo, session);
		
		return applyId;
	}
}
