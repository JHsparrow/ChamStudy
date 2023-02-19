package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Entity.ContentInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDto {
	
	private Long id; //콘텐츠 번호
	
	private String name; //강의이름
	
	private Long videoCount;
	
	private String Url; // 비디오 URL
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ContentInfo createContentInfo() {
		return modelMapper.map(this, ContentInfo.class);
	}
	
	public static VideoDto of(ContentInfo contentInfo) {
		return modelMapper.map(contentInfo, VideoDto.class);
	}
}
