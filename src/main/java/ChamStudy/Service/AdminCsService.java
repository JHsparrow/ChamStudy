package ChamStudy.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsInformDto;
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
	private final AdminCsFileService adminCsService;
	private final AdminCsFileRepository adminCsFileRepository;
	
	//공지사항 게시물 등록
	public Long saveInform(CsInformDto csInformDto, List<MultipartFile> csInformFileList) {
		CsInform csInform = csInformDto.createCsInform();
		adminCsRepository.save(csInform);
		
		//파일 등록
		for(int i=0; i<csInformFileList.size(); i++) {
			CsInformFile csInformFile = new CsInformFile();
			csInformFile.setCsInform(csInform);
		}
		
		
	}

}
