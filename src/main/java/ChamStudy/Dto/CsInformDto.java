package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsInformDto {
	private Long id;
	
	@NotBlank(message = "제목을 입력해 주십시오.")
	private String title;
	
	@NotBlank(message = "내용을 입력해 주십시오.")
	private String substance;
	
	private int viewCount;
	
	private String gubun;
	
	private String fix;
	
	private List<CsInformFileDto> csInformFileList = new ArrayList<>();
	
	private List<Integer> informFileIds = new ArrayList<>();
	
}
