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
@RequestMapping(value="adminApply")
@RequiredArgsConstructor
public class AdminApplyController {
	
	private final AdminCategoryService adminCategoryService;
	
	
	@GetMapping(value = "/list") //메인 카테고리 리스트
	public String applyList(Optional<Integer> page,CategoryDto categoryDto ,Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<CategoryDto> mainList = adminCategoryService.getAllMainList(categoryDto, pageable);
		model.addAttribute("active","category"); // 사이드 바 액티브
		model.addAttribute("mainList", mainList);
		model.addAttribute("maxPage", 5);
		return "AdminForm/adminCategory/mainList";
	}
	
	
	
	
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
	
}
