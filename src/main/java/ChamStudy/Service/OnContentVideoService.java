package ChamStudy.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.OnContentDto;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Repository.OnContentVideoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OnContentVideoService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final OnContentVideoRepository onContentVideoRepository;
	
	private final FileService fileService;
	
	/**
	 * 모든 ContentVideo 조회
	 * @param contentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ContentVideo> getAllContents() {
		//contentVideo 테이블 전체 데이터 조회
		List<ContentVideo> contentVideoList = onContentVideoRepository.findAll();
		
		return contentVideoList;
	}
	
	/**
	 * ContentId 컬럼에 해당하는 ContentVideo 조회
	 * @param contentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ContentVideo> getContents(Long contentId) {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setId(contentId); //join 된 테이블의 content_id 컬럼 값으로 조회하기 위해 설정한다
		
		//content_id 조회값 가져온다 (pk가 아니므로 여러개 반환 가능하여 List 객체 사용)
		List<ContentVideo> contentVideoList = onContentVideoRepository.findByContentInfo(contentInfo);
		
		return contentVideoList;
	}
	
	public Long updateContentVideo(OnContentDto onContentDto, List<MultipartFile> contentVideoFileList) throws Exception {
		//비디오 수정
		//OnContentDto onContentDto;
		
		
		
		return null;
	}
	
}
