package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.VideoDto;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.Completion;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Entity.StudyHistory;
import ChamStudy.Entity.StudyResult;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.ApplyListRepository;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.CompletionRepository;
import ChamStudy.Repository.OnContentRepository;
import ChamStudy.Repository.OnContentVideoRepository;
import ChamStudy.Repository.StudyHistortRepository;
import ChamStudy.Repository.StudyResultRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentVideoService {
	
    private final OnContentVideoRepository contentVideoRepository;
    private final ClassInfoRepository classInfoRepository;
	private final StudyHistortRepository studyHistortRepository;
	private final OnContentRepository onContentRepository;
	private final UserRepository userRepository;
	private final ApplyListRepository applyListRepository;
	private final StudyResultRepository studyResultRepository;
	private final CompletionRepository completionRepository;
	
	
	public void insertVideoData(ContentInfo contentId, int count) {
        for (int i = 1; i <= count; i++) {
            ContentVideo contentVideo = new ContentVideo();
            contentVideo.setName(contentId.getId()+"-" + i);
            contentVideo.setUrl("/video/"+contentId.getId()+"/"+contentId.getId()+"-" + i);
            contentVideo.setContentInfo(contentId);
            contentVideoRepository.save(contentVideo);
        }
    }
	
	public List<ContentVideo> videoList(ContentInfo contentInfo){
		return contentVideoRepository.findByContentInfoOrderById(contentInfo);
	}
	
	public Long getContentId(String videoName) {
		return contentVideoRepository.getContentId(videoName);
	}
	
	public String getContentUrl(String videoUrl) {
		return contentVideoRepository.getContentUrl(videoUrl);
	}
	
	public void createStudyHistory(String videoName, Long contentId, String email) {
		ContentVideo videoId = contentVideoRepository.getId(videoName);
		ContentInfo getContentId = onContentRepository.getContentId(contentId);
		ClassInfo classInfo = classInfoRepository.getClassInfo(contentId);
		Long history_id = studyHistortRepository.getVideoId(videoId.getId());
		UserInfo userId = userRepository.getUserId(email);
		ApplyList applyList = applyListRepository.findByUserId(userId.getId(),classInfo.getId());
		StudyHistory studyHistory = new StudyHistory();
		if(history_id == null) {
			studyHistory.setVideoId(videoId);
			studyHistory.setContentId(getContentId);
			studyHistory.setApplyId(applyList);
		} else {
			studyHistory.setVideoId(videoId);
			studyHistory.setContentId(getContentId);
			studyHistory.setId(history_id);
			studyHistory.setApplyId(applyList);
		}
		studyHistortRepository.save(studyHistory);
	}
	
	
	public void createStudyResult(String email,Long contentId) {
		UserInfo userId = userRepository.getUserId(email);
		ClassInfo classInfo = classInfoRepository.getClassInfo(contentId);
		ApplyList applyId = applyListRepository.findByUserId(userId.getId(),classInfo.getId());
		System.err.println(applyId.getId());
		Long progress = studyHistortRepository.getProgress(applyId.getId(),contentId);
		StudyResult validResult = studyResultRepository.getResultId(applyId.getId());
		System.err.println(validResult.getId());
		StudyResult studyResult = new StudyResult();
		 
		//study_result id가 중복할경우(=applyId가 중복확인)
		if(validResult != null) {
			studyResult.setId(validResult.getId());
		}
		studyResult.setApplyId(applyId);
		studyResult.setProgress(progress);
		studyResultRepository.save(studyResult);
		if(validResult != null) {
			if(validResult.getProgress()>=100) {
				Completion compleId = completionRepository.getCompletion(validResult.getId());
				if(compleId==null) {
					Completion completion = new Completion();
					completion.setResultId(validResult);
					completionRepository.save(completion);
				}
			}
		}
	}
	
	
	
	public Long getProcess(Long contentId) {
		return studyHistortRepository.getCountVideoId(contentId);
	}
	
}
