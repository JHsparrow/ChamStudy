package ChamStudy.Impl;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Entity.UserInfo;

public interface ApplyListRepositoryCustom {
	Long saveApplyClass(ApplyListDto applyListDto, UserInfo session);
}
