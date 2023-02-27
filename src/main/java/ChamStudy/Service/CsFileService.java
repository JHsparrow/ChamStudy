package ChamStudy.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import ChamStudy.Entity.CsInformFile;
import ChamStudy.Repository.CsInformFileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CsFileService {

	@Value("${csFileLocation}")
	private String csFileLocation;
	
	private final CsInformFileRepository adminCsFileRepository;
	
	private final FileService fileService;
	
	//파일 저장
	public void saveFile(CsInformFile csInformFile, MultipartFile csFile) throws Exception {
		String oriFileName = csFile.getOriginalFilename();
		String fileName = "";
		String fileUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriFileName)) {
			fileName = fileService.uploadFile(csFileLocation, oriFileName, csFile.getBytes());
			fileUrl = "/contents/file/" + fileName;
		}
		
		//파일 정보 저장
		csInformFile.updateCsInformFile(oriFileName, fileName, fileUrl);
		adminCsFileRepository.save(csInformFile);
	}
	
	//파일 수정(업데이트)
	public void updateFile(Long informFileId, MultipartFile csInformFile) throws Exception {
		if(!csInformFile.isEmpty()) {
			CsInformFile savedCsFile = adminCsFileRepository.findById(informFileId)
															.orElseThrow(EntityNotFoundException::new);
			
			//기존 파일 삭제
			if(!StringUtils.isEmpty(savedCsFile.getFileName())) {
				fileService.deleteFile(csFileLocation + "/" + savedCsFile);
			}
			
			String oriFileName = csInformFile.getOriginalFilename();
			String fileName = fileService.uploadFile(csFileLocation, oriFileName, csInformFile.getBytes());
			String fileUrl = "/contents/file/" + fileName;
			
			savedCsFile.updateCsInformFile(oriFileName, fileName, fileUrl);
		}
	}
	
	//파일 삭제
	public void deleteInformFile(Long id) {
		CsInformFile csInformFile = adminCsFileRepository.findById(id)
														 .orElseThrow(EntityNotFoundException::new);
		adminCsFileRepository.delete(csInformFile);
	}
	
	
}
