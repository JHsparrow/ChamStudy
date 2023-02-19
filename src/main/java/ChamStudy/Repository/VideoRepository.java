package ChamStudy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import ChamStudy.Entity.ContentVideo;

public interface VideoRepository extends JpaRepository<ContentVideo, Long> {

}
