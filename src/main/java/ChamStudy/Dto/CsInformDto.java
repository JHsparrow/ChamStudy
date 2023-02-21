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
	
	private String gubun;
	
	private List<CsInformFileDto> csInformFileList = new ArrayList<>();
	
	private List<Integer> informFileIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CsInform createCsInform() {
		return modelMapper.map(this, CsInform.class);
	}
	
	public static CsInformDto of (CsInform csInform) {
		return modelMapper.map(csInform, CsInformDto.class);
	}
	
}
