package ChamStudy.Dto;

import java.util.Date;

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
	
	private String teacherName; //강사명
	
	@NotNull(message = "시작일은 필수 입력 값입니다.")
	private String sDate; //시작
	
	@NotNull(message = "종료일은 필수 입력 값입니다.")
	private String eDate; //종료일
	
	private String regDate;	//등록일
	
	private Long contentId; //콘텐츠아이디
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ClassInfo createClassInfo() {
		return modelMapper.map(this, ClassInfo.class);
	}
	
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}
}
