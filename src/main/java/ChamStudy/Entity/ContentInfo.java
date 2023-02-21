package ChamStudy.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="content_info") // 콘텐츠 정보
@Setter
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public class ContentInfo {
	
	@Id
	@Column(name="content_id") 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	@CreatedDate
	@Column(updatable = false)
	private String regDate;
}
