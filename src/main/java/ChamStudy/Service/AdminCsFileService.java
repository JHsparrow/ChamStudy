package ChamStudy.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import ChamStudy.Entity.CsInformFile;
import ChamStudy.Repository.AdminCsFileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCsFileService {

	@Value("${csFileLocation}")
	private String csFileLocation;
	
	private final AdminCsFileRepository adminCsFileRepository;
	
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
	
}
