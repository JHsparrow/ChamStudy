package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CompletionListDto;
import ChamStudy.Dto.UserSearchDto;

public interface UserMainMypageRepositoryCustom {
	
	//완강 리스트 가져오기
	Page<CompletionListDto> getCompletionList(UserSearchDto userSearchDto, CompletionListDto completionListDto, Pageable pageable, Long id);
}
