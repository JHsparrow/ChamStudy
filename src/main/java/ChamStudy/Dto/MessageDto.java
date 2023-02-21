package ChamStudy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageDto {
	private String message;
	private String redirectUri;
}
