package ChamStudy.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CsQnaDto;
import ChamStudy.Entity.CsQna;
import ChamStudy.Entity.CsQnaImg;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import ChamStudy.Repository.CsQnaImgRepository;
import ChamStudy.Repository.CsQnaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CsUserService {
	private final CsQnaRepository csQnaRepository;
	private final CsImgService csImgService;
	private final CsQnaImgRepository csQnaImgRepository;
	private final UserRepository userRepository;
	
	
	//========================================== Qna ==========================================
	
	//Qna 질문 등록
	public Long saveQna(CsQnaDto csQnaDto, List<MultipartFile> csQnaImgList, String email) throws Exception {
		System.out.println("(csUserService)작성자 이메일: " + email);
		
		UserInfo userId = userRepository.getUserId(csQnaDto.getEmail());
		CsQna csQna = csQnaDto.createCsQna();
		
		csQna.setUserId(userId);
		csQna.setChecked("N");
		
		System.out.println("dto에 id 있나용? " + csQnaDto.getId());
		System.out.println("conId가 뭐종? "+csQna.getConId());
		
		csQnaRepository.save(csQna);
		
//		Long id = userId.getId();
//		String role = userRepository.getRole(id);
//		
//		if(role.equals("USER")){
//			csQna.setConId(csQna.getId());
//		} else {
//			csQna.setConId(csQnaDto.getId());
//		}
		
		System.out.println("리스트가 있낭? " + csQnaImgList.get(0).toString());
		System.out.println("리스트가 있낭? " + csQnaImgList.get(0).getContentType());
		System.out.println("리스트가 있낭? " + csQnaImgList.get(0).getOriginalFilename());
		
		//이미지 등록
		for(int i=0; i<csQnaImgList.size(); i++) {
			CsQnaImg csQnaImg = new CsQnaImg();
			csQnaImg.setQnaId(csQna);
			
			csImgService.saveImg(csQnaImg, csQnaImgList.get(i));
		}
		return csQna.getId();
	}
	
	//conId
	public void updateConId() {
		
		CsQna csQna = csQnaRepository.getQna();
		Long conId = csQna.getId();
		csQna.setConId(conId);
		System.out.println("updateConid 뭐게용: " + conId);
	}
	
}
