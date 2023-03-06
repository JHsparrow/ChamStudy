package ChamStudy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import ChamStudy.Entity.Class_review;
import ChamStudy.Impl.ClassReviewListDtoCustom;

public interface Class_reviewRepository extends JpaRepository<Class_review, Long>
												, QuerydslPredicateExecutor<Class_review>
												, ClassReviewListDtoCustom{
	
	Class_review findByClassInfoIdAndUserInfoId(Long classId, Long userId);
	
	Class_review findByIdAndUserInfoId(Long reviewId, Long userId);
	
	/**
	 * review_id 컬럼에 해당하는 class_review 데이터 리스트 조회
	 * 
	 * @EntityGraph : join 된 쿼리가 수행될 수 있도록 하기 위해 사용한다
	 * attributePaths 속성값에는 Entity 객체에 join 을 위해 선언한 join Entity 객체의 변수명을 설정한다
	 */
	@EntityGraph(attributePaths = {"userInfo","classInfo"})
	Optional<Class_review> findById(Long id);
}
