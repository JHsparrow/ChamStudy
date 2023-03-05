package ChamStudy.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import ChamStudy.Dto.Class_reviewDto;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@TableGenerator(
		name = "REVIEW_SEQ_GEN",
	    table = "CUSTOM_SEQUENCE",
	    pkColumnValue = "REVIEW_SEQ",
	    initialValue=1000, //시작값
	    allocationSize = 1
	)
@Table(name="class_review") // 강의 리뷰
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class Class_review extends BaseTimeEntity {
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="REVIEW_SEQ_GEN")
	//generator="CLASS_SEQ_GEN" : 시퀀스 name(generator)설정해놓은 이름으로 설정
	private Long id;
	
	private String description;
	
	private Integer starPoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userInfo; //회원객체
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassInfo classInfo; //강의객체
	
	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
	
	public static Class_review createClass_review(Class_reviewDto class_reviewDto, ClassInfo classInfo, UserInfo userInfo) {
		Class_review class_review = new Class_review();
		class_review.setStarPoint(class_reviewDto.getStarPoint());
		class_review.setDescription(class_reviewDto.getDescription());
		class_review.setClassInfo(classInfo);
		class_review.setUserInfo(userInfo);
		
		return class_review;
	}
}
