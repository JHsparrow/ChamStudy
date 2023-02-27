package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CsFaqListDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Dto.WarnBoardDto;

public interface CsRepositoryCustom {

	//공지사항 리스트 가져오기
	Page<CsInformListDto> getInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable);
	
	//공지사항 상단 고정 리스트 가져오기
	Page<CsInformListDto> getFixedInformList (UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable);

	//자주묻는 질문 리스트 가져오기
	Page<CsFaqListDto> getFaqList(UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Pageable pageable);
	
	//경고게시판 리스트
	Page<WarnBoardDto> getWarnList(UserSearchDto userSearchDto, WarnBoardDto warnBoardDto, Pageable pageable);
	
}
