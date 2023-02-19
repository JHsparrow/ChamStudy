package ChamStudy.Controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.VideoDto;
import ChamStudy.Service.ContentVideoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminContentController {
	
	private final ContentVideoService videoService;
	
	@GetMapping(value = "/adminClass/generation") //기수제 목록
	public String generation() {
		return "AdminClass/list";
	}
	
	@GetMapping(value = "/adminClass/onlineList") //실시간강의 목록
	public String onlineList() {
		return "AdminForm/AdminClass/onlineList";
	}
	
	@GetMapping(value = "/adminClass/onlineDetail") //강의 상세페이지
	public String onlineDetail() {
		return "AdminForm/AdminClass/onlineDetail";
	}
	
	@GetMapping(value = "/contents/videoList") // 콘텐츠 비디오 리스트
	public String videoList() {
		return "AdminForm/adminContent/contentList";
	}
	
	@GetMapping(value = "/contents/videoNew") // 콘텐츠 비디오 등록
	public String videoForm(Model model) {
		model.addAttribute("videoDto", new VideoDto());
		return "AdminForm/adminContent/contentNew";
	}
	
	@PostMapping(value = "/contents/videoNew") // 콘텐츠 비디오 등록
	public String videoCreate(@RequestParam(value = "videoCount") int count) {
		
			videoService.insertData(count);
		
		return "AdminForm/adminContent/contentList";
		
	}
	
	
}
