package ChamStudy.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cs_inform_file")
@Getter
@Setter
@ToString
public class CsInformFile {
	
	@Id
	@Column(name = "inform_file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String oriFileName;
	
	private String fileName;
	
	private String fileUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inform_id")
	private CsInform csInform;
	
	public void updateCsInformFile(String oriFileName, String fileName, String fileUrl) {
		this.oriFileName = oriFileName;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
	}
}
