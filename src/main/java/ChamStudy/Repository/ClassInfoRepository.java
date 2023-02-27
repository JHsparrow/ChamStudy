package ChamStudy.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ChamStudy.Entity.ClassInfo;
import ChamStudy.Impl.ClassInfoRepositoryCustom;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>
										, QuerydslPredicateExecutor<ClassInfo>
										, ClassInfoRepositoryCustom {
	List<ClassInfo> findAll();
	
	@Query(value="select * from class_info c where c.class_name like %:name%", nativeQuery = true)
	List<ClassInfo> findByNameByNative(@Param("name")String name);	
	
	Optional<ClassInfo> findById(Long id);
}