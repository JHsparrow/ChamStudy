package ChamStudy.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
	private String searchCategory;
	private String searchText = ""; //검색 문자열
}
