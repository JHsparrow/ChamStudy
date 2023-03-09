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

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@TableGenerator(
		name = "APPLY_SEQ_GEN",
	    table = "CUSTOM_SEQUENCE",
	    pkColumnValue = "APPLY_SEQ",
	    initialValue=1000, //시작값
	    allocationSize = 1
	)
@Table(name="apply_list") // 강의 리스트
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해
public class ApplyList{
	@Id
	@Column(name="apply_id")
	//generator="APPLY_SEQ_GEN" : 시퀀스 name(generator)설정해놓은 이름으로 설정
	@GeneratedValue(strategy = GenerationType.TABLE, generator="APPLY_SEQ_GEN")
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy년 MM월 dd일")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
    }
	
	public static ApplyList createApplyList(ClassInfo ClassInfo, UserInfo UserInfo) {
		ApplyList applyList = new ApplyList();
		applyList.setComFlag("N");
		applyList.setClassInfo(ClassInfo);
		applyList.setUserInfo(UserInfo);
		return applyList;
	}
	
}
