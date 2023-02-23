package ChamStudy.Entity;

import javax.persistence.*;

import ChamStudy.Dto.CsInformDto;
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
	
	@Column(columnDefinition = "integer default 0", nullable = false)
	private int viewCount;
	
	@Column(nullable = false, columnDefinition = "CHAR", length=1)
	private String gubun;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;
	
	public void updateInform(CsInformDto csInformDto) {
		this.title = csInformDto.getTitle();
		this.substance = csInformDto.getSubstance();
		this.viewCount = csInformDto.getViewCount();
		this.gubun = csInformDto.getGubun();
	}
}
