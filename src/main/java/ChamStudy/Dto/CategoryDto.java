package ChamStudy.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.annotations.QueryProjection;

import ChamStudy.Entity.ContentInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	
	private Long id;
	
	private String name; 
	
	private String date;

	private Long count;
	
	
	@QueryProjection	//querydsl로 결과 조회 시 MainItemDto객체로 바로 받아올 수 있음
    public CategoryDto(Long id, String name, String date, Long count){
        this.id = id;
        this.name = name;
        this.date = date;
        this.count = count;
    }
	
}

