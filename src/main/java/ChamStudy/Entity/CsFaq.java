package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cs_faq")
@Getter
@Setter
@ToString
public class CsFaq {
	
	@Id
	@Column(name = "faq_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String substance;
	
	@CreatedDate 
	@Column(updatable = false)
	private String regDate;
}
