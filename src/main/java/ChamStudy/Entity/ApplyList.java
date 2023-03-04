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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
@SequenceGenerator(
        name="APPLY_SEQ_GEN", //시퀀스 generator 이름
        sequenceName="APPLY_SEQ", //시퀀스 이름
        initialValue=1000, //시작값
        allocationSize = 1 //증가값 (기본증가가 50이라 설정해주어야함)
        )
@Table(name="apply_list") // 강의 리스트
@Getter
@Setter
@ToString
public class ApplyList{
	@Id
	@Column(name="apply_id")
	//generator="APPLY_SEQ_GEN" : 시퀀스 name(generator)설정해놓은 이름으로 설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="APPLY_SEQ_GEN")
	private Long id;
	
	private String comFlag; //수료여부
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassInfo classInfo;
	
	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
	
	public static ApplyList createApplyList(ClassInfo ClassInfo, UserInfo UserInfo) {
		ApplyList applyList = new ApplyList();
		applyList.setComFlag("N");
		applyList.setClassInfo(ClassInfo);
		applyList.setUserInfo(UserInfo);
		return applyList;
	}
	
}
