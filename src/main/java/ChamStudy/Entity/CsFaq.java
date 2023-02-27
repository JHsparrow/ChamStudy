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

import ChamStudy.Dto.CsFaqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cs_faq")
@Getter
@Setter
@ToString
public class CsFaq extends BaseTimeEntity {
	
	@Id
	@Column(name = "faq_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String substance;
	
	@Column(nullable = false, columnDefinition = "CHAR", length = 1)
	private String gubun;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;
	
	
	 public void updateFaq(CsFaqDto csFaqDto) { 
		 this.title = csFaqDto.getTitle(); 
		 this.substance = csFaqDto.getSubstance();
		 this.gubun = csFaqDto.getGubun();
	 }
}
