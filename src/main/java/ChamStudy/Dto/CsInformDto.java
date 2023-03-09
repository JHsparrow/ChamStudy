package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsInform;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsInformDto {
	private Long id;
	
	@NotBlank(message = "제목을 입력해 주십시오.")
	private String title;
	
	@NotBlank(message = "내용을 입력해 주십시오.")
	private String substance;
	
	private int viewCount;
	
	@NotBlank(message = "상단 고정여부를 선택해 주십시오.")
	private String gubun;
	
	private String regdate;
	
	private Long userId;

	private String email;
	
	private List<CsInformFileDto> csInformFileDtoList = new ArrayList<>();
	
	//파일의 아이디 저장 -> 수정시 파일 아이디를 담아 둘 용도
	private List<Long> informFileIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CsInform createCsInform() {
		return modelMapper.map(this, CsInform.class);
	}
	
	public static CsInformDto of (CsInform csInform) {
		return modelMapper.map(csInform, CsInformDto.class);
	}
	
	public CsInformDto() {};
	
	public CsInformDto(CsInform csInform) {
		this.id = csInform.getId();
		this.title = csInform.getTitle();
		this.substance = csInform.getSubstance();
		this.viewCount = csInform.getViewCount();
		this.gubun = csInform.getGubun();
		this.regdate = csInform.getRegDate();
		this.email = csInform.getUserId().getEmail();
	}
}
