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

import ChamStudy.Dto.AdminApplyClassInterface;
import ChamStudy.Dto.AdminApplyClassListDto;
import ChamStudy.Dto.AdminApplySubListDto;
import ChamStudy.Dto.CategoryDto;
import ChamStudy.Dto.CategoryInterface;
import ChamStudy.Dto.MainCategoryDto;
import ChamStudy.Dto.MessageDto;
import ChamStudy.Dto.SubCategoryDto;
import ChamStudy.Dto.SubCategoryJsonDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Dto.modifySubCategoryDto;
import ChamStudy.Entity.Category;
import ChamStudy.Entity.SubCategory;
import ChamStudy.Service.AdminCategoryService;
import ChamStudy.Service.ApplyListService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="adminApply")
@RequiredArgsConstructor
public class AdminApplyController {
	
	private final ApplyListService applyListService;
	
	
	
	@GetMapping(value = "/list") //메인 카테고리 리스트
	public String applyClassList(Model model) {
		List<AdminApplyClassInterface> Lists = applyListService.getAdminApplyClassList();
		model.addAttribute("active","apply"); // 사이드 바 액티브
		model.addAttribute("classLists", Lists);
		return "AdminForm/adminClass/applyClassList";
	}
	@GetMapping(value = "/sublist/{classId}") //메인 카테고리 리스트
	public String applyList(@PathVariable(value = "classId") Long classId ,Optional<Integer> page,AdminApplySubListDto adminApplyListDto ,Model model, UserSearchDto userSearchDto) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<AdminApplySubListDto> Lists = applyListService.getAdminApplyList(adminApplyListDto, pageable, userSearchDto, classId);
		model.addAttribute("active","apply"); // 사이드 바 액티브
		model.addAttribute("applyLists", Lists);
		model.addAttribute("maxPage", 5);
		return "AdminForm/adminClass/applyList";
	}
	
	
	
	
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
	
}
