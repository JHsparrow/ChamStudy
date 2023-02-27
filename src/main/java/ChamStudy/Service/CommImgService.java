package ChamStudy.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;


import ChamStudy.Entity.Comm_Board;
import ChamStudy.Entity.Comm_Board_Img;
import ChamStudy.Repository.CommImgRepository;
import ChamStudy.Repository.CommRepository;
import lombok.RequiredArgsConstructor;

@Service 
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려준다.
@RequiredArgsConstructor
@Component
public class CommImgService {
	
	@Value("${itemImgLocation}")
	private String commImgLocation;
	
	private final CommImgRepository commImgRepository;
	
	private final FileService fileService;
	
	//게시글 아이디로 해당 게시물의 이미지들을 찾아와주는 메소드
	public List<Comm_Board_Img> findImg(Long boardId) throws Exception {
		List<Comm_Board_Img> board_Img = commImgRepository.findByBoardIdOrderByIdAsc(boardId);
		return board_Img;
	}
	
	//이미지 저장 메소드
		public void saveCommImg(Comm_Board_Img board_Img, MultipartFile commImgFile) throws Exception {
			String oriImgName = commImgFile.getOriginalFilename();
			String imgName = "";
			String imgUrl ="";
			
			//파일 업로드
			if(!StringUtils.isEmpty(oriImgName)) {
				imgName = fileService.uploadFile(commImgLocation, oriImgName, commImgFile.getBytes());
				imgUrl = "/images/comm/" + imgName;
			}
			
			//상품 이미지 정보 저장
			board_Img.updateBoardImg(oriImgName, imgName, imgUrl);
			commImgRepository.save(board_Img);
		}
		
		//이미지 업데이트 메소드
		public void updateItemImg(Long commImgId, MultipartFile commImgFile) throws Exception {
			if(!commImgFile.isEmpty()) { //파일이 있으면
				Comm_Board_Img savedcommImg = commImgRepository.findById(commImgId).orElseThrow(EntityNotFoundException::new);
				
				//기존 이미지 파일 삭제
				if(!StringUtils.isEmpty(savedcommImg.getName())) {
					fileService.deleteFile(commImgLocation + "/" + savedcommImg.getName());
				}
				
				//수정된 이미지 파일 업로드
				String oriImgName = commImgFile.getOriginalFilename();
				String imgName = fileService.uploadFile(commImgLocation, oriImgName, commImgFile.getBytes());
				String imgUrl = "/images/item" + imgName;
				
				//★ savedItemImg는 현재 영속상태이므로 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가 실행됨
				// -> 엔티티가 반드시 영속상태여야 한다.
				savedcommImg.updateBoardImg(oriImgName, imgName, imgUrl);
			}
			
		}
	
	
}
