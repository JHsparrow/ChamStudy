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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import ChamStudy.Dto.ClassInfoDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//https://dololak.tistory.com/479 시퀀스 참조사이트
@Entity
@SequenceGenerator(
        name="CLASS_SEQ_GEN", //시퀀스 generator 이름
        sequenceName="CLASS_SEQ", //시퀀스 이름
        initialValue=1000, //시작값
        allocationSize = 1 //증가값 (기본증가가 50이라 설정해주어야함)
        )
@Table(name="class_info") // 클래스 정보
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class ClassInfo {
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CLASS_SEQ_GEN")
	//generator="CLASS_SEQ_GEN" : 시퀀스 generator설정해놓은 이름으로 설정
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private ContentInfo contentInfo;
	
}
