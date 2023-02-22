package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.CsInformFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsInformFileDto {
	private Long id;
	
	private String oriFileName;
	
	private String fileName;
	
	private String fileUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static CsInformFileDto of(CsInformFile csInformFile) {
		return modelMapper.map(csInformFile, CsInformFileDto.class);
	}
}
