package ChamStudy.Dto;

import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.UserInfo;

public class applyListDto {

	private Long id; //수강신청리스트아이디
	
	private String comFlag; //수료여부

	private UserInfo UserInfo; //회원 아이디
	
	private ClassInfo ClassInfo; //강의 아이디
	
	private String regDate; //등록날짜
}
