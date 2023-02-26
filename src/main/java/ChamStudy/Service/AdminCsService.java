package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsInformDto;
import ChamStudy.Dto.CsInformFileDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.CsInform;
import ChamStudy.Entity.CsInformFile;
import ChamStudy.Repository.AdminCsFileRepository;
import ChamStudy.Repository.AdminCsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCsService {
	private final AdminCsRepository adminCsRepository;
	private final AdminCsFileService adminCsFileService;
	private final AdminCsFileRepository adminCsFileRepository;
	
	//공지사항 게시물 등록
	public Long saveInform(CsInformDto csInformDto, List<MultipartFile> csInformFileList) throws Exception {
		CsInform csInform = csInformDto.createCsInform();
		adminCsRepository.save(csInform);
		
		//파일 등록
		for(int i=0; i<csInformFileList.size(); i++) {
			CsInformFile csInformFile = new CsInformFile();
			csInformFile.setInformId(csInform);
			
			adminCsFileService.saveFile(csInformFile, csInformFileList.get(i));
		}
		return csInform.getId();
	}
	
	//게시물 불러오기
	@Transactional(readOnly = true)
	public CsInformDto getInform(Long informId) {
		//csInformFile 테이블의 파일을 가져온다.
		List<CsInformFile> csInformFileList = adminCsFileRepository.findByInformIdOrderByIdAsc(informId);
		List<CsInformFileDto> csInformFileDtoList = new ArrayList<>();
		
		//엔티티 객체 -> DTO 객체로 변환
		for (CsInformFile csInformFile : csInformFileList) {
			CsInformFileDto csInformFileDto = CsInformFileDto.of(csInformFile);
			csInformFileDtoList.add(csInformFileDto);
		}
		
		//csInform 테이블에 있는 데이터를 가져온다.
		CsInform csInform = adminCsRepository.findById(informId)
											 .orElseThrow(EntityNotFoundException::new);
		
		CsInformDto csInformDto = CsInformDto.of(csInform);
		
		csInformDto.setCsInformFileDtoList(csInformFileDtoList);
		
		return csInformDto;
	}
	
	//게시물 수정하기
	public Long updateInform(CsInformDto csInformDto, List<MultipartFile> csInformFileList) throws Exception {
		CsInform csInform = adminCsRepository.findById(csInformDto.getId())
											 .orElseThrow(EntityNotFoundException::new);
		csInform.updateInform(csInformDto);
		
		List<Long> informFileIds = csInformDto.getInformFileIds();
		
		for(int i = 0; i < csInformFileList.size(); i++) {
			adminCsFileService.updateFile(informFileIds.get(i), csInformFileList.get(i));
		}
		return csInform.getId();
				
	}
	
	//공지사항 첫 화면 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<CsInformListDto> getInformList (UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable){
		return adminCsRepository.getInformList(userSearchDto, csInformListDto, pageable);
	}
	
	
	//공지사항 상단 고정 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<CsInformListDto> getFixedInformList (UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable){
		return adminCsRepository.getFixedInformList(userSearchDto, csInformListDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public int NumberOfFixed() {
		return adminCsRepository.findByGubun();
	}
	
	public void deleteInform(Long id) {
		CsInform csInform = adminCsRepository.findById(id)
											 .orElseThrow(EntityNotFoundException::new);
		adminCsRepository.delete(csInform);
		
	}
}
