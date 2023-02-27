package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.ContentDto;

public interface ContentRepositoryCustom {

	//회원 리스트 가져오기
	Page<ContentDto> getContentPage(ContentDto contentDto, Pageable pageable);
}
