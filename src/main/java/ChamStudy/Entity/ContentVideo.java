package ChamStudy.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="content_video") //콘텐츠 비디어 테이블
@Setter
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public class ContentVideo {
	
	@Id
	@Column(name="content_video_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name; 
	
	private String oriname;
	
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private ContentInfo contentInfo;
	
	@CreatedDate
	@Column(updatable = false)
	private String regDate;

}
