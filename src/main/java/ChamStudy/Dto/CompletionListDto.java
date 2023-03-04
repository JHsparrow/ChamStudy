package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionListDto {
	
	private Long id;
	
	//메인 카테고리 이름
	private String categoryName;
	
	//서브 카테고리 이름
	private String subCategoryName;
	
	//강의명
	private String className;
	
	//학습률
	private int progress;
	
	//학습 시작일
	private String startDate;
	
	//수료일
	private String endDate;
	
	//콘텐츠 아이디(재생버튼)
	private Long contentId;
	
	//콘텐츠 이미지
	private String imgUrl;
	
	//콘텐츠 이미지 이름
	private String oriImgName;
	
}
