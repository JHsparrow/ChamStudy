package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsQnaImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsQnaImgDto {
	
	private Long id;
	
	private String oriImgName;
	
	private String imgName;
	
	private String imgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static CsQnaImgDto of(CsQnaImg csQnaImg) {
		return modelMapper.map(csQnaImg, CsQnaImgDto.class);
	}
}
