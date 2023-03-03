package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "study_history")
@Getter
@Setter
@ToString
public class StudyHistory extends BaseTimeEntity {
	
	@Id
	@Column(name = "history_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_id")
	private ApplyList applyId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id")
	private ContentVideo videoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private ContentInfo contentId;
	
	
}
