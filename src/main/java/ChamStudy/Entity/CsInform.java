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
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String substance;
	
	@Column(columnDefinition = "interger default 0", nullable = false)
	private int viewCount;
	
	@Column(nullable = false, columnDefinition = "CHAR", length=1)
	private String gubun;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;
}
