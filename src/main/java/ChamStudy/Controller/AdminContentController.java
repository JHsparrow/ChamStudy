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
import ChamStudy.Service.ContentVideoService;
import ChamStudy.Service.OnContentService;
import ChamStudy.Service.OnContentVideoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminContentController {
	
	private final ContentVideoService videoService;
	private final OnContentService onContentService;
	private final OnContentVideoService onContentVideoService;
	
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
		
		List<ContentInfo> contentInfo = onContentService.getAllContent();
		model.addAttribute("contentInfo", contentInfo);
		
		return "/AdminForm/AdminOnClass/contentList";
	}
	
	@GetMapping(value = "/adminOnClass/contentVideo") //콘텐츠비디오 관리페이지
	public String contentVideo() {	
		return "/AdminForm/AdminOnClass/contentVideo";
	}
	
	//@GetMapping일때는 @PathVariable(value = "contentId") Long contentId)으로 가져온다.
	@GetMapping(value = "/adminOnClass/contentVideo/{contentId}") //콘텐츠 비디오 보기
	public String contents(Model model, @PathVariable(value = "contentId") Long contentId) {
	
		List<ContentVideo> contentVideoList = onContentVideoService.getContents(contentId);
		model.addAttribute("contentVideoList", contentVideoList);
	
		return "/AdminForm/AdminOnClass/contentVideo";
	
	}

	@GetMapping(value = "/adminOnClass/contentNew") //콘텐츠 등록 화면
	public String contentForm(Model model) {
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
				//contentVideoList 조회
				List<ContentVideo> contentVideoList = onContentVideoService.getContents(contentId);
				OnContentDto onContentDto = OnContentDto.of(contentInfo);
				
				List<OnContentVideoDto> videoDtoList = new ArrayList<OnContentVideoDto>();
				for(int i=0; i<contentVideoList.size(); i++) {
					ContentVideo contentVideo  = contentVideoList.get(i);
					OnContentVideoDto onContentVideoDto = OnContentVideoDto.of(contentVideo);
					videoDtoList.add(onContentVideoDto);
				}
				
				model.addAttribute("errorMessage", "정상적으로 조회되었습니다");
				model.addAttribute("onContentDto", onContentDto);
				model.addAttribute("onContentVideoDtoList", videoDtoList);
				model.addAttribute("onContentVideoDtoListSize", videoDtoList.size());
				
				return "/AdminForm/AdminOnClass/contentUpdate";
			}
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "조회 중 에러가 발생했습니다.");
			model.addAttribute("onContentDto", new OnContentDto());
			model.addAttribute("onContentVideoDtoList", new ArrayList<OnContentVideoDto>());
			model.addAttribute("onContentVideoDtoListSize", 0);
			
			return "/AdminForm/AdminOnClass/contentList";
		}
	}
	
	/*
	@PostMapping(value = "/adminOnClass/contentUpdate") //콘텐츠 수정
	public String contentUpdate(@Valid OnContentDto onContentDto, BindingResult bindingResult,	Model model, 
			@RequestParam(value = "id") Long contentId) {
		try {
			//DTO를 ENTITY로 변환
			ContentInfo contentInfo = onContentDto.createContentInfo();
			
			
			//조회된 데이터가 있을 경우
			if (contentInfo != null) {
				
				
				//contentInfo데이터 수정
				contentInfo = onContentService.save(contentInfo);
				model.addAttribute(contentInfo);
				
				//콘텐츠 이미지 조회
				
				//contentVideo데이터 조회
				
				//contentVideo데이터 수정
				
				//model.addAttribute("errorMessage", "콘텐츠 수정이 성공적으로 등록되었습니다!");
				
				return "/AdminForm/AdminOnClass/contentUpdate";
			}
			//조회된 데이터가 없을 경우
			else {
				model.addAttribute("onContentDto", new OnContentDto());		
			}
			
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 콘텐츠 입니다.");
			model.addAttribute("onContentDto", new OnContentDto());
		}
		
		return "/AdminForm/AdminOnClass/contentUpdate";
	}
	*/
	
	/*
	@DeleteMapping(value = "/adminOnClass/contentDelete/{contentId}") 
	public String contentDelete(Model model, @RequestParam(value="contentId") Long contentId) {
		try {
			int result = onContentService.deleteContent(contentId);
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "콘텐츠 삭제에 실패했습니다.");
		}
	}
	*/
}
