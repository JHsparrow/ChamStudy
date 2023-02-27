package ChamStudy.Dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsFaq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsFaqDto {
	
	private Long id;
	
	@NotBlank(message = "제목을 입력해 주십시오.")
	private String title;
	
	@NotBlank(message = "내용을 입력해 주십시오.")
	private String substance;
	
	@NotBlank(message = "카테고리를 선택해 주십시오.")
	private String gubun;
	
	private String update;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CsFaq createCsFaq() {
		return modelMapper.map(this, CsFaq.class);
	}
	
	public static CsFaqDto of (CsFaq csFaq) {
		return modelMapper.map(csFaq, CsFaqDto.class);
	}
}
