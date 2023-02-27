package ChamStudy.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsFaqDto;
import ChamStudy.Dto.CsFaqListDto;
import ChamStudy.Dto.CsInformDto;
import ChamStudy.Dto.CsInformFileDto;
import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Dto.WarnBoardDto;
import ChamStudy.Entity.CsFaq;
import ChamStudy.Entity.CsInform;
import ChamStudy.Entity.CsInformFile;
import ChamStudy.Repository.CsFaqRepository;
import ChamStudy.Repository.CsInformFileRepository;
import ChamStudy.Repository.CsInformRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CsService {
	private final CsInformRepository csInformRepository;
	private final CsFileService csFileService;
	private final CsInformFileRepository csInformFileRepository;
	
	private final CsFaqRepository csFaqRepository;
	
	//========================================== 공지사항 ==========================================
	
	//공지사항 게시물 등록
	public Long saveInform(CsInformDto csInformDto, List<MultipartFile> csInformFileList) throws Exception {
		CsInform csInform = csInformDto.createCsInform();
		csInformRepository.save(csInform);
		
		//파일 등록
		for(int i=0; i<csInformFileList.size(); i++) {
			CsInformFile csInformFile = new CsInformFile();
			csInformFile.setInformId(csInform);
			
			csFileService.saveFile(csInformFile, csInformFileList.get(i));
		}
		return csInform.getId();
	}
	
	//게시물 불러오기
	@Transactional(readOnly = true)
	public CsInformDto getInform(Long informId) {
		//csInformFile 테이블의 파일을 가져온다.
		List<CsInformFile> csInformFileList = csInformFileRepository.findByInformIdOrderByIdAsc(informId);
		List<CsInformFileDto> csInformFileDtoList = new ArrayList<>();
		
		//엔티티 객체 -> DTO 객체로 변환
		for (CsInformFile csInformFile : csInformFileList) {
			CsInformFileDto csInformFileDto = CsInformFileDto.of(csInformFile);
			csInformFileDtoList.add(csInformFileDto);
		}
		
		//csInform 테이블에 있는 데이터를 가져온다.
		CsInform csInform = csInformRepository.findById(informId)
											 .orElseThrow(EntityNotFoundException::new);
		
		CsInformDto csInformDto = CsInformDto.of(csInform);
		
		csInformDto.setCsInformFileDtoList(csInformFileDtoList);
		
		return csInformDto;
	}
	
	//게시물 수정하기
	public Long updateInform(CsInformDto csInformDto, List<MultipartFile> csInformFileList) throws Exception {
		CsInform csInform = csInformRepository.findById(csInformDto.getId())
											 .orElseThrow(EntityNotFoundException::new);
		csInform.updateInform(csInformDto);
		
		List<Long> informFileIds = csInformDto.getInformFileIds();
		
		for(int i = 0; i < csInformFileList.size(); i++) {
			csFileService.updateFile(informFileIds.get(i), csInformFileList.get(i));
		}
		return csInform.getId();
	}
	
	//공지사항 첫 화면 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<CsInformListDto> getInformList (UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable){
		return csInformRepository.getInformList(userSearchDto, csInformListDto, pageable);
	}
	
	
	//공지사항 상단 고정 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<CsInformListDto> getFixedInformList (UserSearchDto userSearchDto, CsInformListDto csInformListDto, Pageable pageable){
		return csInformRepository.getFixedInformList(userSearchDto, csInformListDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public int NumberOfFixed() {
		return csInformRepository.findByGubun();
	}
	
	public void deleteInform(Long id) {
		CsInform csInform = csInformRepository.findById(id)
											 .orElseThrow(EntityNotFoundException::new);
		csInformRepository.delete(csInform);
		
	}
	
	//========================================== 자주묻는질문 ==========================================
	
	//자주묻는질문 게시물 등록
	public Long saveFaq(CsFaqDto csFaqDto) throws Exception {
		CsFaq csFaq = csFaqDto.createCsFaq();
		csFaqRepository.save(csFaq);
		
		return csFaq.getId();
	}
	
	//자주묻는질문 첫 화면 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<CsFaqListDto> getFaqList (UserSearchDto userSearchDto, CsFaqListDto csFaqListDto, Pageable pageable){
		return csFaqRepository.getFaqList(userSearchDto, csFaqListDto, pageable);
	}

	
	//자주묻는질문 게시글 불러오기
	@Transactional(readOnly = true)
	public CsFaqDto getFaq(Long faqId) {
		
		//csInform 테이블에 있는 데이터를 가져온다.
		CsFaq csFaq = csFaqRepository.findById(faqId)
											 .orElseThrow(EntityNotFoundException::new);
		
		CsFaqDto csFaqDto = CsFaqDto.of(csFaq);
		
		return csFaqDto;
	}
	
	//자주묻는질문 수정하기
	public Long updateFaq(CsFaqDto csFaqDto) throws Exception {
		CsFaq csFaq = csFaqRepository.findById(csFaqDto.getId())
											 .orElseThrow(EntityNotFoundException::new);
		csFaq.updateFaq(csFaqDto);
		
		return csFaq.getId();
	}
	
	//자주묻는질문 삭제하기
	public void deleteFaq(Long id) {
		CsFaq csFaq = csFaqRepository.findById(id)
											 .orElseThrow(EntityNotFoundException::new);
		csFaqRepository.delete(csFaq);
	}
	
	//========================================== 경고게시판 ==========================================
	
	// 경고게시판 리스트 출력
	public Page<WarnBoardDto> getWarnList (UserSearchDto userSearchDto, WarnBoardDto warnBoardDto, Pageable pageable){
		return csFaqRepository.getWarnList(userSearchDto, warnBoardDto, pageable);
	}

}
