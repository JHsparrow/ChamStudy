package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.CertificateDto;
import ChamStudy.Dto.CompletionListDto;

public interface CertificateRepositoryCustom {

	//회원 리스트 가져오기
	Page<CertificateDto> getCompletionPage(CertificateDto certificateDto, Pageable pageable, String email);
}
