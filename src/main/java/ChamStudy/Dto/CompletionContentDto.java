package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionContentDto {
	
	//콘텐츠 아이디
	private Long contentId;
	
	//강의명
	private String className;
	
	//학습률
	private Long progress;
	
	//학습 시작일
	private String startDate;
	
	//학습 종료일
	private String endDate;
	
	//비디오 아이디
	private Long videoId;
	
	//비디오 url
	private String videoUrl;
	
	//비디오 이름
	private String videoName;
	
	//유저 아이디
	private Long userId;
}
