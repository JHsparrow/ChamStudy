package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ChamStudy.Entity.Comm_Board;

public interface CommRepository extends JpaRepository<Comm_Board, Integer> {
	
}
