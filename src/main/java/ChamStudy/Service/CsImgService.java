package ChamStudy.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import ChamStudy.Entity.CsQnaImg;
import ChamStudy.Repository.CsQnaImgRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CsImgService {

	@Value("${csImgLocation}")
	private String csImgLocation;
	
	private final CsQnaImgRepository csQnaImgRepository;
	
	private final FileService fileService;
	
	//이미지 저장
	public void saveImg(CsQnaImg csQnaImg, MultipartFile qnaImg) throws Exception {
		String oriImgName = qnaImg.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		//이미지 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(csImgLocation, oriImgName, qnaImg.getBytes());
			imgUrl = "/contents/img/" + imgName;
		}
		
		//이미지 정보 저장
		csQnaImg.updateCsQnaImg(oriImgName, imgName, imgUrl);
		csQnaImgRepository.save(csQnaImg);
	}
}
