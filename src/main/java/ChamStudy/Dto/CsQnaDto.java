package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsQna;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsQnaDto {
	
	private Long id;
	
	private String category;
	
	private String title;
	
	private String substance;
	
	private String update;
	
	private String flag;
	
	private String gubun;

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
