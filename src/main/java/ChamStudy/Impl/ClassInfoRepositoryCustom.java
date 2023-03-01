package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;

public interface ClassInfoRepositoryCustom {
	Page<ClassInfoListDto> joinContent(Pageable pageable); //강의리스트 조회(페이징)
	
	Page<ClassInfoListDto> findByClassList(ClassInfoDto adminClassDto, Pageable pageable); //강의리스트 검색(콘텐츠명)
	
	ClassInfoListDto findByClassDetail(ClassInfoDto adminClassDto); //강의 사용자 상세페이지
	
	long updateByClassDetail(ClassInfoDto adminClassDto); //강의리스트 수정
}
