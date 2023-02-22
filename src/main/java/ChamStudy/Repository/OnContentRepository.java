package ChamStudy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.ContentInfo;

public interface OnContentRepository extends JpaRepository<ContentInfo, Long> {
	List<ContentInfo> findAll();
}
