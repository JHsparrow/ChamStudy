package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.UserInfoDto;
import ChamStudy.Dto.UserListDto;
import ChamStudy.Dto.UserSearchDto;

public interface UserRepositoryCustom {

	//회원 리스트 가져오기
	Page<UserListDto> getUserPage(UserSearchDto userSearchDto,UserInfoDto userInfoDto,Pageable pageable);
}
