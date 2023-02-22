package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.Comm_Board_Img;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommImgDto {
	private Long id;
	
	private String Name;
	
	private String oriName;
	
	private String url;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static CommImgDto of(Comm_Board_Img comm_Board_Img) {
		return modelMapper.map(comm_Board_Img, CommImgDto.class);
	}
}
