package ChamStudy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.VideoDto;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Repository.OnContentVideoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentVideoService {
	
	@Autowired
    private OnContentVideoRepository contentVideoRepository;
	
	public void insertData(int count) {
		System.out.println("카운트 : " + count);
        for (int i = 1; i <= count; i++) {
            ContentVideo contentVideo = new ContentVideo();
            contentVideo.setName("이름테스트");
            contentVideo.setUrl("테스트 - " + i);
            contentVideoRepository.save(contentVideo);
        }
    }
}
