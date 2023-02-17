package ChamStudy.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cs_inform")
@Getter
@Setter
@ToString
public class CsInform extends BaseTimeEntity {
	
	@Id
	@Column(name = "inform_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String substance;
	
	private int viewCount;
	
	@Column(nullable = false)
	private String gubun;
	
	private String fix;
}
