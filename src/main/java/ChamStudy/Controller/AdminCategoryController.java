package ChamStudy.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ChamStudy.Dto.MessageDto;
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
	public String mainCategoryList(Model model) {
		List<Category> mainList = adminCategoryService.getAllMainList();
		model.addAttribute("mainList", mainList);
		return "AdminForm/adminCategory/mainList";
	}
	
	@GetMapping(value = "/sub/{mainid}") //메인 카테고리 리스트
	public String subCategoryList(@PathVariable("mainid") Long mainId, Model model) {
		List<SubCategory> subList = adminCategoryService.getAllSubList(mainId);
		model.addAttribute("mainList", subList);
		return "AdminForm/adminCategory/mainList";
	}
	
	@GetMapping(value = "/new") //메인 카테고리 생성 페이지
	public String mainCategoryCreateForm() {
		return "AdminForm/adminCategory/mainNew";
	}
	
	
	@GetMapping(value = "/newResult") //메인 카테고리 생성 처리 페이지
	public String mainCategoryCreate(@RequestParam(value = "cateName") String cateName, Model model) {
		MessageDto message;
		try {
			System.out.println(cateName);
			adminCategoryService.saveCategory(cateName);
			System.out.println("1-1");
			message = new MessageDto("메인카테고리 생성이 완료되었습니다.", "/adminCategory/main");
		} catch (Exception e) {
			message = new MessageDto("메인카테고리 생성이 실패하였습니다.", "/adminCategory/main");
		}
		
        return showMessageAndRedirect(message, model);
	}
	
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
	
}
