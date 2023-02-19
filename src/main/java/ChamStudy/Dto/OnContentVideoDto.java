package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.ContentVideo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnContentVideoDto {
	
	private Long id; //콘텐츠비디오번호
	
	private String name; //콘텐츠비디오파일명
	
	private String oriname; //원본 콘텐츠비디오파일명
	
	private String url; //콘텐츠비디오경로
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static OnContentVideoDto of(ContentVideo contentVideo) {
		return modelMapper.map(contentVideo, OnContentVideoDto.class);
	}
	
}
