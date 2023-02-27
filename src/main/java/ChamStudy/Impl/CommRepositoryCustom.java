package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.MainCommDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Entity.Comm_Board;

public interface CommRepositoryCustom  {
	Page<Comm_Board> getAdminComm(CommSearchDto commSearchDto, Pageable pageable);
	
	Page<MainCommDto> getAdminMainCommDto(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable);
	
	Page<MainCommDto> getAdminQnACommDto(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable);
	
	Page<MainCommDto> getAdminMentoCommDto(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable);
}
