package ChamStudy.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.OnContentDto;
import ChamStudy.Dto.OnContentVideoDto;
import ChamStudy.Dto.VideoDto;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Service.AdminCategoryService;
import ChamStudy.Service.ContentVideoService;
import ChamStudy.Service.OnContentService;
import ChamStudy.Service.OnContentVideoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminContentController {
	
	private final ContentVideoService videoService;
	private final OnContentService onContentService;
	private final AdminCategoryService adminCategoryService;
	
	//콘텐츠
	@GetMapping(value = "/adminOnClass/contents") //콘텐츠 관리페이지
	public String contents(Model model) {
		
		List<ContentInfo> contentInfo = onContentService.getAllContent();
		model.addAttribute("contentInfo", contentInfo);
		return "/AdminForm/AdminOnClass/contentList";
	}
	
	@GetMapping(value = "/adminOnClass/contentVideoList/{contentId}") //콘텐츠비디오 교안보기 리스트
	public String contentVideoList(@PathVariable("contentId") ContentInfo contentId, Model model) {	
		List<ContentVideo> videoLists = videoService.videoList(contentId);
		model.addAttribute("videoLists",videoLists);
		return "/AdminForm/AdminOnClass/contentVideoList";
	}
	
	@GetMapping(value = "/adminOnClass/video/{videoUrl}") //콘텐츠비디오 재생페이지
	public String showContentVideo(@PathVariable("videoUrl") String videoUrl, Model model) {	
		Long contentId = videoService.getContentId(videoUrl);
		String url = videoService.getContentUrl(videoUrl);
		videoService.createStudyHistory(videoUrl,contentId);
		model.addAttribute("contentId",contentId);
		model.addAttribute("videoUrl",url);
		return "/AdminForm/AdminOnClass/contentVideo";
	}
	
	
	
	
	@GetMapping(value = "/adminOnClass/contentNew") //콘텐츠 등록
	public String contentForm(Model model) throws Exception {
		model.addAttribute("cateList",adminCategoryService.getCategory());

		model.addAttribute("onContentDto", new OnContentDto());
		return "/AdminForm/AdminOnClass/contentNew";
	}
	
	@PostMapping(value = "/adminOnClass/contentNew") //콘텐츠 등록
	public String contentNew(@Valid OnContentDto onContentDto, BindingResult bindingResult,	Model model) {
		MessageDto message;
		if(bindingResult.hasErrors()) {
			return "/AdminForm/AdminOnClass/contentNew";
		}
		try {
			onContentService.saveOnContent(onContentDto);
			ContentInfo conId = onContentService.getLastContentId();
			videoService.insertVideoData(conId,5);
			message = new MessageDto("콘텐츠 등록이 완료되었습니다.", "/adminOnClass/contents");
		} catch (Exception e) {
			message = new MessageDto("콘텐츠 등록이 실패하였습니다.", "/AdminForm/AdminOnClass/contentNew");
		}
		return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/adminOnClass/contentUpdate/{contentId}") //수정할 컨텐츠 조회
	public String contentUpdateGet(Model model, @PathVariable(value = "contentId") Long contentId) {
		
		try {
			//contentInfo데이터 조회
			ContentInfo contentInfo = null;
			contentInfo = onContentService.getIdContent(contentId);
			
			if (contentInfo == null) {
				model.addAttribute("errorMessage", "존재하지 않는 콘텐츠 입니다.");
				model.addAttribute("onContentDto", new OnContentDto());
				model.addAttribute("onContentVideoDtoList", new ArrayList<OnContentVideoDto>());
				model.addAttribute("onContentVideoDtoListSize", 0);
				
				return "/AdminForm/AdminOnClass/contentNew";
			}
			else {
				OnContentDto onContentDto = OnContentDto.of(contentInfo);
				model.addAttribute("errorMessage", "정상적으로 조회되었습니다");
				model.addAttribute("onContentDto", onContentDto);
				return "/AdminForm/AdminOnClass/contentUpdate";
			}
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "조회 중 에러가 발생했습니다.");
			model.addAttribute("onContentDto", new OnContentDto());
			return "/AdminForm/AdminOnClass/contentList";
		}
	}
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
}
