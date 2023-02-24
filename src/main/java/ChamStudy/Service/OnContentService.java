package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.OnContentDto;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Repository.OnContentRepository;
import ChamStudy.Repository.OnContentVideoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OnContentService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final OnContentRepository onContentRepository;
	private final OnContentVideoService onContentVideoService;
	private final OnContentVideoRepository onContentVideoRepository;
	private final FileService fileService;
	
	
	/**
	 * 콘텐츠 등록
	 * Transactional을 위해 콘텐츠정보와 비디오 등록을 같이함
	 * @param onContentDto
	 * @return 0보다 크면 정상
	 */
	public Long saveOnContent(OnContentDto onContentDto) throws Exception {
		try {
			MultipartFile itemImgFile = onContentDto.getItemImgFile();
			List<MultipartFile> contentVideoList = onContentDto.getContentVideoFileList();
			
			//dto객체에서 contentInfo데이터(엔티티객체)를 생성
			ContentInfo contentInfo = onContentDto.createContentInfo();
			
			//contentInfo데이터 저장 : 이미지 원본파일명 저장
			String oriImgName = itemImgFile.getOriginalFilename();
			
			if (oriImgName == null || oriImgName.length() == 0) {
				throw new Exception("이미지 파일이 선택되지 않았습니다");
			}
			
			//한번에 저장처리하기 위해 주석처리 후 밑에서 구현
			contentInfo.setOriImgName(oriImgName);
			
			//클라이언트에서 전달한 이미지 파일 서버에 저장
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			
			//contentInfo데이터 저장 : 서버에 저장된 이미지 파일명 저장
			contentInfo.setImgName(imgName);
			
			//contentInfo데이터 저장 : 업로드된 이미지 url 저장
			String imgUrl = "/contents/" + imgName;
			contentInfo.setImgUrl(imgUrl);
			
			//등록날짜
			//LocalDateTime localDateTime = LocalDateTime.now();
			//String regDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
			//contentInfo.setRegDate(regDate);
			
			//데이터베이스 저장 : OnContentRepository 에 contentInfo데이터를 저장
			contentInfo = onContentRepository.save(contentInfo);
			
			
			//for문(비디오콘텐츠)
			for(int i=0; i<contentVideoList.size(); i++) {
				
				//데이터베이스에 contentVideo데이터 저장
				ContentVideo contentVideo = new ContentVideo();
				
				//비디오 파일 개수 만큼 실행 start
				MultipartFile multipartFile = contentVideoList.get(i); //List에서 하나씩 꺼내기
				
				//contentInfo데이터 저장 : 비디오 원본파일명 저장
				String orivideoNm = multipartFile.getOriginalFilename();
				
				if (orivideoNm == null || orivideoNm.length() == 0) {
					continue; //넘어온 비디오 파일이 없으면 다음 비디오 파일 실행
				}
				
				contentVideo.setOriname(orivideoNm);
				
				//클라이언트에서 전달한 비디오 파일 서버에 저장
				String videoNm = fileService.uploadFile(itemImgLocation, orivideoNm, multipartFile.getBytes());	 
				contentVideo.setName(videoNm);
				
				//contentInfo데이터 저장 : 업로드된 비디오 url 저장
				String videoUrl = "/contents/" + videoNm;
				contentVideo.setUrl(videoUrl);
				
				//contentId를 contentVideo에도 저장한다.
				contentVideo.setContentInfo(contentInfo);
				
				//데이터베이스 저장 : OnContentRepository 에 contentVideo데이터를 저장
				contentVideo = onContentVideoRepository.save(contentVideo);
				
			} //비디오 파일 개수 만큼 실행 end
			
			return contentInfo.getId();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public List<ContentInfo> getAllContent() {
		//content 테이블 전체 데이터 조회
		List<ContentInfo> contentInfo = onContentRepository.findAll();
		
		return contentInfo;
	}
	
	@Transactional(readOnly = true)
	public ContentInfo getIdContent(Long contentId) {
		//content테이블에서 contentId 조회
		Optional<ContentInfo> contentInfo = onContentRepository.findById(contentId);
		ContentInfo result = null;
		
		result = contentInfo.orElse(null); //조회된 값 없을 때 null 로 가져온다
		
		return result;
	}
	
	/**
	 * ContentInfo insert, update
	 * @param contentInfo
	 * @return
	 */
	public ContentInfo save(ContentInfo contentInfo) {
		return onContentRepository.save(contentInfo);
	}
	
	/**
	 * 콘텐츠 삭제
	 * @param contentId
	 * @return
	 * 양수: 삭제된 콘텐츠 수
	 * -1 : contentId에 해당하는 콘텐츠가 없다.
	 */
	public int deleteContent(Long contentId) {
		
		Optional<ContentInfo> contentInfo = onContentRepository.findById(contentId);
		
		//해당 콘텐츠가 존재 하지 않으면
		if(contentInfo == null) {
			return -1;
		} else {
			onContentRepository.deleteById(contentId);
			return 1;
		}
	}
	
}
