package ChamStudy.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cs_inform_file")
@Getter
@Setter @SequenceGenerator(
		name="INFORMFILE_GEN_GEN",	//공지사항 시퀀스
		sequenceName="INFORMFILE_SEQ",	//시퀀스 이름
		initialValue=5000	//시작값
		)
@ToString
public class CsInformFile {
	
	@Id
	@Column(name = "inform_file_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "INFORMFILE_GEN_GEN")
	private Long id;
	
	private String oriFileName;
	
	private String fileName;
	
	private String fileUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inform_id")
	private CsInform informId;
	
	//파일 업로드
	public void updateCsInformFile(String oriFileName, String fileName, String fileUrl) {
		this.oriFileName = oriFileName;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
	}
	
}
