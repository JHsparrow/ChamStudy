package ChamStudy.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "warn_board")
public class WarnBoard {
	@Id
	@Column(name = "warn_id") //식별자
	private Long id;
	
	@Column(name = "reporter_id") // 신고하는 사람의 아이디
	private Long reporterId;
	
	@Column(name = "reported_id") // 신고를 받는 대상자의 아이디
	private Long reportedId;
	
	@Column(name = "warn_type") // 신고 유형
	private String warnType;
	
	@Column(name = "descripton")
	private String description; //기타 설명(신고 사유)
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;
	
	private String boardType; //신고 게시판 구분
	
	private Long boardId; // 신고 게시판 아이디
	
	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm:ss")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
    }
	
	
	
	
}