package ChamStudy.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import ChamStudy.Entity.ClassInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoDto {

	private Long id; //클래스 번호
	
	@NotBlank(message = "강의이름은 필수 입력 값입니다.")
	private String name; //강의이름
	
	@NotNull(message = "수강료는 필수 입력 값입니다.")
	private Integer price; //수강료
	
	@NotNull(message = "인원제한수는 필수 입력 값입니다.")
	private Integer peopleNum; //인원제한
	
	private String teacherName; //강사명
	
	private String sDate; //시작
	
	private String eDate; //종료일
	
	private String regDate;	//등록일
	
	private String contentId; //콘텐츠아이디
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ClassInfo createClassInfo() {
		return modelMapper.map(this, ClassInfo.class);
	}
	
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}
}
