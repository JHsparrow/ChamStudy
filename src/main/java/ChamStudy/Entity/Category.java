package ChamStudy.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import ChamStudy.Dto.modifySubCategoryDto;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="category")
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class Category {
	
	@Id
	@Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH:mm:ss")
	private String regDate;
	
	@PrePersist
    public void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
    }
	
	public void updateMainCategory(String cateName) {
		this.name = cateName;
	}
}
