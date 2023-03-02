package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsQna;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsQnaDto {
	
	private Long id;
	
	@NotBlank(message="제목을 입력해 주세요.")
	private String title;
	
	@NotBlank(message="문의 내용을 입력해 주세요.")
	private String substance;
	
	private String update;

	private Long conId;
	
	private Long userId;
	
	private String email;

	private List<Long> qnaImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CsQna createCsQna() {
		return modelMapper.map(this, CsQna.class);
	}
	
	public static CsQnaDto of (CsQna csQna) {
		return modelMapper.map(csQna, CsQnaDto.class);
	}
}
