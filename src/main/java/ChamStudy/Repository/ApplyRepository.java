package ChamStudy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ChamStudy.Entity.ApplyList;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.ContentVideo;

public interface ApplyRepository extends JpaRepository<ApplyList, Long> {
	@Query(value= "select count(*) from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id where d.name = ?1",nativeQuery = true)
	Long countMain(String mainName);
	
	@Query(value= "select count(*) from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id inner join sub_category e on d.category_id = e.category_id and c.sub_category_id = e.sub_category_id where d.name = ?1 and e.name = ?2",nativeQuery = true)
	Long countMainSub(String mainName, String subName); 
}
