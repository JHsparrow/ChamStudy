package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Dto.ChartInterface;
import ChamStudy.Entity.ApplyList;
import ChamStudy.Impl.ApplyListRepositoryCustom;

public interface ApplyListRepository extends JpaRepository<ApplyList, Long>
								, QuerydslPredicateExecutor<ApplyList>
								, ApplyListRepositoryCustom {
	@Query(value= "select count(*) from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id where d.name = ?1",nativeQuery = true)
	Long countMain(String mainName);
	
	@Query(value= "select count(*) from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id inner join sub_category e on d.category_id = e.category_id and c.sub_category_id = e.sub_category_id where d.name = ?1 and e.name = ?2",nativeQuery = true)

	Long countMainSub(String mainName, String subName); 
	
	ApplyList findByUserInfoId(Long userId);
	
	ApplyList findByClassInfoIdAndUserInfoId(Long classId, Long userId);

	
	@Query(value="select d.name as name, count(*) as count from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id group by a.class_id", nativeQuery = true)
	List<ChartInterface> countChartMain();
	
	@Query(value="select e.name as name, count(*) as count from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id inner join sub_category e on d.category_id = e.category_id and c.sub_category_id = e.sub_category_id where d.name='IT' group by a.class_id order by e.name", nativeQuery = true)
	List<ChartInterface> countChartSubIt();
	@Query(value="select e.name as name, count(*) as count from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id inner join sub_category e on d.category_id = e.category_id and c.sub_category_id = e.sub_category_id where d.name='자격증' group by a.class_id order by e.name", nativeQuery = true)
	List<ChartInterface> countChartSubCertificate();
	@Query(value="select e.name as name, count(*) as count from apply_list a inner join class_info b on a.class_id = b.class_id inner join content_info c on c.content_id = b.content_id inner join category d on d.category_id = c.category_id inner join sub_category e on d.category_id = e.category_id and c.sub_category_id = e.sub_category_id where d.name='어학' group by a.class_id order by e.name", nativeQuery = true)
	List<ChartInterface> countChartSubLanguage();

}
