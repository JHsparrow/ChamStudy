package ChamStudy.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
<<<<<<< HEAD
	private LocalDateTime regDate; //등록날짜
	
	@LastModifiedDate
	private LocalDateTime updDate; //수정날짜
=======
	private LocalDateTime reg_date; //등록날짜
	
	@LastModifiedDate
	private LocalDateTime up_date; //수정날짜
>>>>>>> 2ff06f48b1324b01f39e9f647a4d8debff34ed0f
}
