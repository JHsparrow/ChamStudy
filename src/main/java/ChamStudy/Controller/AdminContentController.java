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
	
	@GetMapping(value = "/adminOnClass/contentVideo") //콘텐츠비디오 관리페이지
	public String contentVideo() {	
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
}
