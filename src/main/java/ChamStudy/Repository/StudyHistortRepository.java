package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Entity.StudyHistory;
import ChamStudy.Entity.SubCategory;

public interface StudyHistortRepository extends JpaRepository<StudyHistory, Long> {
	
	@Query(value="select * from study_history where video_id = ?1 and apply_id = ?2 ",nativeQuery = true)
	Long getVideoId(Long videoId, Long apply_id); 
	
	@Query(value="select count(*) from study_history where content_id = ?1 ",nativeQuery = true)
	Long getCountVideoId(Long contentId); 
	
	
	@Query(value="select * from study_history where content_id = ?1 ",nativeQuery = true)
	List<StudyHistory> getHistoryList(Long ContentId);
	
	@Query(value="select count(*)*20 from study_history where apply_id = ?1 and content_id = ?2 ",nativeQuery = true)
	Long getProgress(Long applyId, Long contentId); 
}
