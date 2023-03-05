package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyClassLearningDto {
	private Long id; //apply_list.id

	private String reg_date; //강의 신청일

	private UserInfo userInfo; //유저 정보

	private ClassInfo classInfo; //강의 정보

 	private String imgUrl; //강의 이미지

	private String name; //메인 카테고리
		
	private Long progress; //학습률
	
	private Long contentId; //강의 콘텐츠 ID
	
	private String subName; //서브 카테고리

	@QueryProjection
	public MyClassLearningDto(Long id, String reg_date, UserInfo userInfo, ClassInfo classInfo, String imgUrl,
			String name, Long progress, Long content_id, String subName) {
		this.id = id;
		this.reg_date = reg_date;
		this.userInfo = userInfo;
		this.classInfo = classInfo;
		this.imgUrl = imgUrl;
		this.name = name;
		this.progress = progress;
		this.contentId = content_id;
		this.subName = subName;
	}
}
