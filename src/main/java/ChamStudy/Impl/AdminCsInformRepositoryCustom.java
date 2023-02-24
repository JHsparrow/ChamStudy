package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.UserSearchDto;

public interface AdminCsInformRepositoryCustom {

	//공지사항 리스트 가져오기
	Page<CsInformListDto> getInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable);
}
