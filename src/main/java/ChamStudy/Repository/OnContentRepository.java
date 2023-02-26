package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.SubCategory;

public interface OnContentRepository extends JpaRepository<ContentInfo, Long> {
	List<ContentInfo> findAll();
	
	Optional<ContentInfo> findById(Long id);
	
	void deleteById(Long id);
	
	@Query(value="select content_id from content_info order by content_id desc limit 1",nativeQuery = true)
	ContentInfo getTop1();
	
	@Query(value="select * from content_info where content_id = ?1",nativeQuery = true)
	ContentInfo getContentId(Long contentId);
}
