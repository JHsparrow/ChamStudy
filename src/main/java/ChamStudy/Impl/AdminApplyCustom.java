package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.AdminApplyListDto;
import ChamStudy.Dto.UserSearchDto;

public interface AdminApplyCustom {
	Page<AdminApplyListDto> getAdminApplyList(AdminApplyListDto adminApplyListDto, Pageable pageable,
			UserSearchDto userSearchDto);
}
