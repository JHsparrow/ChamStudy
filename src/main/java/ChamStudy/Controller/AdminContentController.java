package ChamStudy.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.ContentDto;
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
	public String contents(Model model, Optional<Integer> page, ContentDto contentDto) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 2);
		Page<ContentDto> contentInfo = onContentService.getAllContnetList(contentDto, pageable);
		
//		List<ContentInfo> contentInfo = onContentService.getAllContent();
		model.addAttribute("contentInfo", contentInfo);
		model.addAttribute("maxPage", 5);
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
	
	private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
	}
	
	@GetMapping(value = "/adminOnClass/del") //메인 카테고리 삭제 처리 페이지
	public String CategoryDelete(@RequestParam(value = "contentId") Long contentId, Model model) {
		MessageDto message;
		try {
			System.out.println("123");
			onContentService.deleteContentInfo(contentId);
			message = new MessageDto("콘텐츠 삭제가 완료되었습니다.", "/adminOnClass/contents");
		} catch (Exception e) {
			message = new MessageDto("콘텐츠 삭제가 실패하였습니다.", "/adminOnClass/contents");
		}
        return showMessageAndRedirect(message, model);
	}
}
