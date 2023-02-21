package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.OnContentDto;
import ChamStudy.Dto.VideoDto;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Service.ContentVideoService;
import ChamStudy.Service.OnContentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminContentController {
	
	private final ContentVideoService videoService;
	private final OnContentService onContentService;
	
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
	
	//콘텐츠
	@GetMapping(value = "/adminOnClass/contents") //콘텐츠 관리페이지
	public String contents(Model model) {
		/*
		전체 콘텐츠 조회
		List<ContentVideo> contentVideoList = onContentService.getAllContents();
		model.addAttribute("contentVideoList", contentVideoList);
		*/
		
		List<ContentVideo> contentVideoList = onContentService.getContents((long) 1);
		model.addAttribute("contentVideoList", contentVideoList);
		
		return "/AdminForm/AdminOnClass/contentList";
	}
	
	@GetMapping(value = "/adminOnClass/contentNew") //콘텐츠 등록
	public String contentForm(Model model) {
		model.addAttribute("onContentDto", new OnContentDto());
		return "/AdminForm/AdminOnClass/contentNew";
	}
	
	@PostMapping(value = "/adminOnClass/contentNew")
	public String contentNew(@Valid OnContentDto onContentDto, BindingResult bindingResult,	Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/AdminForm/AdminOnClass/contentNew";
		}
		
		try {
			if(0 < onContentService.saveOnContent(onContentDto)) {
				model.addAttribute("errorMessage", "콘텐츠 등록이 성공적으로 등록되었습니다!");
				return "/AdminForm/AdminOnClass/contentNew";
			} else {
				model.addAttribute("errorMessage", "콘텐츠 등록이 실패했습니다!");
				return "/AdminForm/AdminOnClass/contentNew";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "콘텐츠 등록 중 에러가 발생했습니다.");
			return "/AdminForm/AdminOnClass/contentNew";
		}
	}
}
