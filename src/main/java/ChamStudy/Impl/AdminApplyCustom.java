package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.AdminApplyClassListDto;
import ChamStudy.Dto.AdminApplySubListDto;
import ChamStudy.Dto.UserSearchDto;

public interface AdminApplyCustom {
	Page<AdminApplySubListDto> getAdminApplySubList(AdminApplySubListDto adminApplySubListDto, Pageable pageable,
			UserSearchDto userSearchDto, Long classId);
}
