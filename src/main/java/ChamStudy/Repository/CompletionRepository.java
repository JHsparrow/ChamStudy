package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Dto.CertificateInterface;
import ChamStudy.Dto.CommMentoClassNameDto;
import ChamStudy.Dto.CompletionContentInterface;
import ChamStudy.Entity.Completion;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Impl.CertificateRepositoryCustom;
import ChamStudy.Impl.UserMainMypageRepositoryCustom;


public interface CompletionRepository extends JpaRepository<Completion, Long>,
QuerydslPredicateExecutor<Completion>, UserMainMypageRepositoryCustom, CertificateRepositoryCustom {
	
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
	
	
	
	@Query(value="select e.content_id as contentId, d.class_name as className,d.class_id as classId  ,b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from apply_list A \r\n"
			+ "left join study_result B on b.apply_id = a.apply_id\r\n"
			+ "join class_info D on d.class_id = a.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = a.user_id\r\n"
			+ "where d.class_id = ? limit 1;", nativeQuery = true)
	CompletionContentInterface getApplyContentOne(Long classId);
	
	
	@Query(value="select c.class_name as className, A.content_id as contentId, A.video_id as videoId, b.name as contentName, c.class_id as classId \r\n"
			+ "from content_video A \r\n"
			+ "join content_info B on a.content_id = b.content_id\r\n"
			+ "join class_info C on b.content_id = c.content_id\r\n"
			+ "where b.content_id = ?1 and c.class_id = ?2 " , nativeQuery = true)
	List<CompletionContentInterface> getApplyContent(Long contentId, Long classId);
	
	
	@Query(value="select e.content_id as contentId, d.class_name as className, d.class_id as classId ,b.progress, b.reg_date as startDate, d.e_date as endDate,\r\n"
			+ "f.video_id as videoId, f.url as videoUrl, f.oriname as videoName, e.name as contentName, z.user_id as userId\r\n"
			+ "from apply_list A \r\n"
			+ "left join study_result B on b.apply_id = a.apply_id\r\n"
			+ "join class_info D on d.class_id = a.class_id\r\n"
			+ "join content_info E on e.content_id = d.content_id\r\n"
			+ "join content_video F on f.content_id = e.content_id\r\n"
			+ "join user_info Z on z.user_id = a.user_id\r\n"
			+ "where z.user_email = ?1 and f.video_id = ?2 and d.class_id = ?3 ;", nativeQuery=true)
	CompletionContentInterface getLearningContentOther(String email, Long videoId, Long classId);
	
	

	@Query(value="select d.class_name from completion a \r\n"
			+ "join study_result b on a.result_id = b.result_id\r\n"
			+ "join apply_list c on b.apply_id = c.apply_id\r\n"
			+ "join class_info d on d.class_id = c.class_id\r\n"
			+ "join user_info e on e.user_id = c.user_id\r\n"
			+ "where e.user_email = ?;", nativeQuery = true)
	List<String> getClassName(String email);

	//수료증 정보
	@Query(value="select a.completion_id as compId , d.class_name as className, d.class_id as classId, f.user_name as userName, c.reg_date as applyDate, a.reg_date as compDate \r\n"
			+ "from completion a\r\n"
			+ "join study_result B on a.result_id = b.result_id\r\n"
			+ "join apply_list C on b.apply_id = c.apply_id\r\n"
			+ "join class_info d on c.class_id = d.class_id\r\n"
			+ "join content_info e on e.content_id = d.content_id\r\n"
			+ "join user_info f on c.user_id = f.user_id\r\n"
			+ "where a.completion_id = ?1", nativeQuery = true)
	CertificateInterface getCertificateInfo(Long compId);
	

}