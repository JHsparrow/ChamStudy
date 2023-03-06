package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;

public interface OnContentVideoRepository extends JpaRepository<ContentVideo, Long> {
	/**
	 * 전체 검색
	 * 
	 * @Override : findAll() 메소드는 JpaRepository 에서 기본으로 제공하는 메소드라서 @Override 를 사용해야 한다
	 * @EntityGraph : join 된 쿼리가 수행될 수 있도록 하기 위해 사용한다
	 * attributePaths 속성값에는 Entity 객체에 join 을 위해 선언한 join Entity 객체의 변수명을 설정한다
	 */
	@Override
	@EntityGraph(attributePaths = {"contentInfo"})
	List<ContentVideo> findAll();
	
	/**
	 * content_id 컬럼에 해당하는 contentVideo 데이터 리스트 조회
	 * 
	 * @EntityGraph : join 된 쿼리가 수행될 수 있도록 하기 위해 사용한다
	 * attributePaths 속성값에는 Entity 객체에 join 을 위해 선언한 join Entity 객체의 변수명을 설정한다
	 */
	@EntityGraph(attributePaths = {"contentInfo"})
	List<ContentVideo> findByContentInfo(ContentInfo contentInfo);
	
	
	List<ContentVideo> findByContentInfoOrderById(ContentInfo contentInfo);
	
	@Query(value="select content_id from content_video where name = ?1 ",nativeQuery = true)
	Long getContentId(String videoName); 
	
	@Query(value="select url from content_video where name = ?1 ",nativeQuery = true)
	String getContentUrl(String videoUrl); 
	
	@Query(value="select * from content_video where name = ?1 ",nativeQuery = true)
	ContentVideo getId(String videoName); 
	
	@Query(value="select * from content_video where content_id = ?1 limit 1" , nativeQuery = true)
	ContentVideo getVideoIdF(Long contentId);
	
	@Query(value="select * from content_video where video_id = ?1" , nativeQuery = true)
	ContentVideo getVideoId(Long videoId);
	
	
	
	@Query(value="select * from content_video where content_id = ?1 ",nativeQuery = true)
	List<ContentVideo> getListContentId(Long contentId);
	
}
