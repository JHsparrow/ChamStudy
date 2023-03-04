package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Dto.MyClassLearningDto;
import ChamStudy.Dto.MyClassLearningSearchDto;
import ChamStudy.Entity.UserInfo;

public interface ApplyListRepositoryCustom {

	Page<MyClassLearningDto> getLearningDto(MyClassLearningDto classLearningDto,Pageable pageable,MyClassLearningSearchDto classLearningSearchDto,String email);
}
