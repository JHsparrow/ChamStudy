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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//https://dololak.tistory.com/479 시퀀스 참조사이트
@Entity
@TableGenerator(
		name = "CLASS_SEQ_GEN",
	    table = "CUSTOM_SEQUENCE",
	    pkColumnValue = "CLASS_SEQ",
	    initialValue=1000, //시작값
	    allocationSize = 1
	)
@Table(name="class_info") // 클래스 정보
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class ClassInfo {
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="CLASS_SEQ_GEN")
	//generator="CLASS_SEQ_GEN" : 시퀀스 name(generator)설정해놓은 이름으로 설정
	private Long id;
	
	@Column(name="class_name")
	private String name;	
	
	private Integer price;
	
	private String teacherName;
	
	private Integer peopleNum;
	
	private String sDate;
	
	private String eDate;
	
	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy년 MM월 dd일")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private ContentInfo contentInfo;
	
}
