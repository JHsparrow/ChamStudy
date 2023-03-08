package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

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
	private Long progress;
	
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
	
	private Long classId;
	
	@QueryProjection
	public CompletionListDto(Long id, String categoryName, String subCategoryName, String className,
			Long progress, String startDate, String endDate, Long contentId, String imgUrl, String oriImgName, Long classId) {
		this.id = id;
		this.categoryName = categoryName;
		this.subCategoryName = subCategoryName;
		this.className = className;
		this.progress = progress;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contentId = contentId;
		this.imgUrl = imgUrl;
		this.oriImgName = oriImgName;
		this.classId = classId;
	}
	
}
