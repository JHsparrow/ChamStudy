package ChamStudy.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.MainCommDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Entity.Comm_Board;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommSearchService {
	private final CommRepository commRepository;
	
	//게시판 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<Comm_Board> getCommPage(CommSearchDto commSearchDto, Pageable pageable){
		return commRepository.getAdminComm(commSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainCommDto> getmainCommPage(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable){
		return commRepository.getAdminMainCommDto(commSearchDto, adminMainCommDto ,pageable);
	}

	@Transactional(readOnly = true)
	public Page<MainCommDto> getQnACommPage(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable){
		return commRepository.getAdminQnACommDto(commSearchDto, adminMainCommDto ,pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainCommDto> getMentoCommPage(CommSearchDto commSearchDto,MainCommDto adminMainCommDto ,Pageable pageable){
		return commRepository.getAdminMentoCommDto(commSearchDto, adminMainCommDto ,pageable);
	}
	
}
