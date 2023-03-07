package ChamStudy.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateDto {
	private Long id;
	private String name;
	private String date;
	private String img;
	
	@QueryProjection
    public CertificateDto(Long id, String name, String date, String img){
        this.id = id;
        this.name = name;
        this.date = date;
        this.img = img;
    }
	
}

