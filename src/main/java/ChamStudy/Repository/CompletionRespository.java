package ChamStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.Completion;

public interface CompletionRespository extends JpaRepository<Completion, Long>,
QuerydslPredicateExecutor<Completion> {
	
	@Query(value ="select e.name from completion A \r\n"
			+ "join apply_list B on b.apply_id = a.apply_id\r\n"
			+ "join class_info C on c.class_id = b.class_id\r\n"
			+ "join content_info D on d.content_id = c.content_id\r\n"
			+ "join category E on e.category_id = d.category_id\r\n"
			+ "join user_info Z on z.user_id = b.user_id;", nativeQuery = true)
	String getCategoryName();

}
