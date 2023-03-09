package ChamStudy.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.SubCategoryJsonDto;
import ChamStudy.Dto.modifySubCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Service.AdminCategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="adminCategory")
@RequiredArgsConstructor
public class AdminCategoryController {
	
	private final AdminCategoryService adminCategoryService;
	
	
	@GetMapping(value = "/main") //메인 카테고리 리스트
	public String mainCategoryList(Optional<Integer> page,CategoryDto categoryDto ,Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<CategoryDto> mainList = adminCategoryService.getAllMainList(categoryDto, pageable);
		model.addAttribute("active","category"); // 사이드 바 액티브
		model.addAttribute("mainList", mainList);
		model.addAttribute("maxPage", 5);
		return "adminForm/adminCategory/mainList";
	}
	
	@GetMapping(value = "/sub/{mainid}") //서브 카테고리 리스트
	public String subCategoryList(Optional<Integer> page, @PathVariable("mainid") Long mainId,SubCategoryDto subCategoryDto, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Category mainInfo = adminCategoryService.getMainInfo(mainId);
		Page<SubCategoryDto> subList = adminCategoryService.getSubList(subCategoryDto, pageable, mainInfo);
		
		model.addAttribute("active","category"); // 사이드 바 액티브
		model.addAttribute("subList", subList);
		model.addAttribute("mainInfo", mainInfo);
		model.addAttribute("maxPage", 5);
		return "adminForm/adminCategory/subList";
	}
	
	@GetMapping(value = "/consub/{mainid}") //콘텐츠 등록 서브 카테고리
	@ResponseBody
	public List<SubCategoryJsonDto> subCategoryFonContent(@PathVariable("mainid") Long mainId) { 
		List<SubCategory> subList = adminCategoryService.getAllSubList(mainId);
		List<SubCategoryJsonDto> subCategoryJsonDtos = new ArrayList<>();
		for (SubCategory subCategory : subList) {
			subCategoryJsonDtos.add(new SubCategoryJsonDto(subCategory));
		}
		return subCategoryJsonDtos;
	}
	
	
	@GetMapping(value = "/new") //메인 카테고리 생성 페이지
	public String mainCategoryCreateForm() {
		return "adminForm/adminCategory/mainNew";
	}
	
	
	@GetMapping(value = "/newResult") //메인 카테고리 생성 처리 페이지
	public String mainCategoryCreate(@RequestParam(value = "cateName") String cateName, Model model) {
		MessageDto message;
		try {
			adminCategoryService.saveCategory(cateName);
			message = new MessageDto("메인카테고리 생성이 완료되었습니다.", "/adminCategory/main");
		} catch (Exception e) {
			message = new MessageDto("메인카테고리 생성이 실패하였습니다.", "/adminCategory/main");
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	@PostMapping(value = "/newSubResult") //서브 카테고리 생성 처리 페이지
	public String subCategoryCreate(@RequestParam(value = "mainId") Category mainId, @RequestParam(value = "cateName") String cateName,
			@RequestParam("subImg") MultipartFile subImg, Model model) {
		MessageDto message;
		System.out.println("메인아이디 : "+mainId.getId());
		
		try {
			System.err.println("11111111111");
			SubCategory subCategory = adminCategoryService.saveSubCategory(mainId,cateName);
			System.err.println("22222222222");
			adminCategoryService.saveSubCategoryImg(subCategory, subImg, mainId.getId());
			System.err.println("33333333333");
			message = new MessageDto("서브카테고리 생성이 완료되었습니다.", "/adminCategory/sub/"+mainId.getId());
		} catch (Exception e) {
			message = new MessageDto("서브카테고리 생성이 실패하였습니다.", "/adminCategory/sub/"+mainId.getId());
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/modifyResult") //메인 카테고리 수정 처리 페이지
	public String subCategoryModify(@RequestParam(value = "mainId")Long mainId, @RequestParam(value = "cateName")String cateName, Model model) {
		MessageDto message;
		try {
			adminCategoryService.updateMainCategory(mainId, cateName);
			message = new MessageDto("서브카테고리 수정이 완료되었습니다.", "/adminCategory/main");
		} catch (Exception e) {
			message = new MessageDto("서브카테고리 수정이 실패하였습니다.", "/adminCategory/main");
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	@PostMapping(value = "/modifySubResult") //서브 카테고리 수정 처리 페이지
	public String subCategoryModify(@Valid modifySubCategoryDto modifySubCategoryDto, BindingResult bindingResult, 
			@RequestParam("subImg") MultipartFile subImg, Model model) {
		MessageDto message;
		try {
			SubCategory subCategory = adminCategoryService.updateSubCategory(modifySubCategoryDto);
			adminCategoryService.saveSubCategoryImgUp(subCategory, subImg,modifySubCategoryDto.getSubId());
			message = new MessageDto("서브카테고리 수정이 완료되었습니다.", "/adminCategory/sub/"+modifySubCategoryDto.getMainId());
		} catch (Exception e) {
			message = new MessageDto("서브카테고리 수정이 실패하였습니다.", "/adminCategory/sub/"+modifySubCategoryDto.getMainId());
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/del") //메인 카테고리 삭제 처리 페이지
	public String CategoryDelete(@RequestParam(value = "mainId") Long mainId, Model model) {
		MessageDto message;
		try {
			adminCategoryService.deleteMainCategory(mainId);
			message = new MessageDto("메인카테고리 삭제가 완료되었습니다.", "/adminCategory/main");
		} catch (Exception e) {
			message = new MessageDto("메인카테고리 삭제가 실패하였습니다.", "/adminCategory/main");
		}
        return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = "/subDel") //서브 카테고리 삭제 처리 페이지
	public String subCategorydelete(@RequestParam(value = "mainId") Long mainId, @RequestParam(value = "subId") Long subId, Model model) {
		MessageDto message;
		try {
			adminCategoryService.deleteSubCategory(subId);
			message = new MessageDto("서브카테고리 삭제가 완료되었습니다.", "/adminCategory/sub/"+mainId);
		} catch (Exception e) {
			message = new MessageDto("서브카테고리 삭제가 실패하였습니다.", "/adminCategory/sub/"+mainId);
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	
	
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
	
}
