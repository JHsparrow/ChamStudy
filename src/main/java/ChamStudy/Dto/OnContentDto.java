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
public class OnContentDto {
	
	private Long id; //콘텐츠 번호
	
	@NotBlank(message = "강의이름은 필수 입력 값입니다.")
	private String name; //강의이름
	
	private String imgName; //콘텐츠 이미지 파일명
	
	private String oriImgName; //원본 콘텐츠 이미지 파일명
	
	private String imgUrl; // 콘텐츠 이미지 경로
	
	private MultipartFile itemImgFile; //콘텐츠 이미지 정보를 저장하는 객체
	
	private Long categoryId;
	
	private Long subCategoryId;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ContentInfo createContentInfo() {
		return modelMapper.map(this, ContentInfo.class);
	}
	
	public static OnContentDto of(ContentInfo contentInfo) {
		return modelMapper.map(contentInfo, OnContentDto.class);
	}
}
