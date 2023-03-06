package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Dto.CompletionContentInterface;
import ChamStudy.Entity.Completion;
import ChamStudy.Impl.UserMainMypageRepositoryCustom;


public interface CompletionRepository extends JpaRepository<Completion, Long>,
QuerydslPredicateExecutor<Completion>, UserMainMypageRepositoryCustom {
	
	@Query(value="select * from completion where result_id = ?1", nativeQuery = true)
	Completion getCompletion(Long resultId); 
	
	@Query(value ="select e.name from completion A \r\n"
			+ "join apply_list B on b.apply_id = a.apply_id\r\n"
			+ "join class_info C on c.class_id = b.class_id\r\n"
			+ "join content_info D on d.content_id = c.content_id\r\n"
			+ "join category E on e.category_id = d.category_id\r\n"
			+ "join user_info Z on z.user_id = b.user_id;", nativeQuery = true)
	String getCategoryName();
	
	@Query(value="select e.content_id as contentId, d.class_name as className, b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from completion A \r\n"
			+ "left join study_result B on b.result_id = a.result_id\r\n"
			+ "join apply_list C on c.apply_id = b.apply_id\r\n"
			+ "join class_info D on d.class_id = c.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = c.user_id\r\n"
			+ "where e.content_id = ?;", nativeQuery = true)
	List<CompletionContentInterface> getCompeltionContent(Long contentId);
	
	@Query(value="select e.content_id as contentId, d.class_name as className, b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from completion A \r\n"
			+ "left join study_result B on b.result_id = a.result_id\r\n"
			+ "join apply_list C on c.apply_id = b.apply_id\r\n"
			+ "join class_info D on d.class_id = c.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = c.user_id\r\n"
			+ "where e.content_id = ? limit 1;", nativeQuery = true)
	CompletionContentInterface getCompeltionContentOne(Long contentId);
	
	@Query(value="select e.content_id as contentId, d.class_name as className, b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from completion A \r\n"
			+ "left join study_result B on b.result_id = a.result_id\r\n"
			+ "join apply_list C on c.apply_id = b.apply_id\r\n"
			+ "join class_info D on d.class_id = c.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = c.user_id\r\n"
			+ "where e.content_id = ?1 and f.video_id = ?2 ;", nativeQuery=true)
	CompletionContentInterface getCompeltionContentOther(Long contentId, Long videoId);
	
	
	
	@Query(value="select e.content_id as contentId, d.class_name as className, b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from apply_list A \r\n"
			+ "left join study_result B on b.apply_id = a.apply_id\r\n"
			+ "join class_info D on d.class_id = a.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = a.user_id\r\n"
			+ "where e.content_id = ? limit 1;", nativeQuery = true)
	CompletionContentInterface getApplyContentOne(Long contentId);
	
	
	@Query(value="select c.class_name as className, A.content_id as contentId, A.video_id as videoId, b.name as contentName, c.class_id as classId \r\n"
			+ "from content_video A \r\n"
			+ "join content_info B on a.content_id = b.content_id\r\n"
			+ "join class_info C on b.content_id = c.content_id\r\n"
			+ "where b.content_id = ?1 \r\n"
			+ "group by a.name", nativeQuery = true)
	List<CompletionContentInterface> getApplyContent(Long contentId);
	
	
	@Query(value="select e.content_id as contentId, d.class_name as className, b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from apply_list A \r\n"
			+ "left join study_result B on b.apply_id = a.apply_id\r\n"
			+ "join class_info D on d.class_id = a.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = a.user_id\r\n"
			+ "where e.content_id = ?1 and f.video_id = ?2 and d.class_id = ?3 ;", nativeQuery=true)
	CompletionContentInterface getLearningContentOther(Long contentId, Long videoId, Long classId);
	
	//classInfo 구하기
	
	

}
