package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.ClassInfoListDto;

public interface ClassInfoRepositoryCustom {
	Page<ClassInfoListDto> joinContent(Pageable pageable);
}
