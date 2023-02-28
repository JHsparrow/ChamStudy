package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoListDto {

	private Long id; //클래스 번호
	
	private String name; //강의이름
	
	private Integer price; //수강료
	
	private Integer peopleNum; //인원제한
	
	private String teacherName; //강사명
	
	private String sDate; //시작
	
	private String eDate; //종료일
	
	private String regDate;	//등록일
	
	private Long contentId; //콘텐츠아이디
	
	private String contentName;
	
	@QueryProjection //querydsl로 결과 조회 시 MainItemDto객체로 바로 받아올 수 있음
    public ClassInfoListDto(Long id, String name, Integer price, Integer peopleNum, String regDate,
    		String teacherName, String sDate, String eDate, Long contentId, String contentName ){
        this.id = id;
        this.name = name;
        this.peopleNum = peopleNum;
        this.teacherName = teacherName;
        this.price = price;
        this.regDate = regDate;
        this.sDate = sDate;
        this.eDate = eDate;
        this.contentId = contentId;
        this.contentName = contentName;
    }
	
	
}
