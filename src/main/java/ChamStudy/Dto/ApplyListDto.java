package ChamStudy.Dto;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Entity.ApplyList;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApplyListDto {

	private Long id; //수강신청리스트아이디
	
	private String comFlag; //수료여부

	private Long userId; //회원 아이디
	
	private Long classId; //강의 아이디
	
	private String regDate; //등록날짜
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ApplyList createApplyList() {
		return modelMapper.map(this, ApplyList.class);
	}
	
	public static ApplyList of(ApplyList applyList) {
		return modelMapper.map(applyList, ApplyList.class);
	}
	
	@QueryProjection
	public ApplyListDto(Long id, String comFlag, Long userId, Long classId, String regDate) {
		this.id  = id;
		this.comFlag = comFlag;
		this.userId = userId;
		this.classId = classId;
		this.regDate = regDate;
	}
}
