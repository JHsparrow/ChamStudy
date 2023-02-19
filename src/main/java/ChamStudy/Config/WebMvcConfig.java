package ChamStudy.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${uploadPath}") //프로퍼티의 값을 읽어온다.
	String uploadPath;

	//웹 브라우저에 입력하는 url이 /contents로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어온다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/contents/**")
		        .addResourceLocations(uploadPath);
	}
}
