package ChamStudy.Controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ChamStudy.Dto.MainCommDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Service.CommService;
import ChamStudy.Service.CommSearchService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/userForm")
public class CommController { //커뮤니티 컨트롤러
	private final CommService adminCommService;
	private final CommSearchService commSearchService;
	
	@GetMapping(value = {"/comm","comm/{page}"})
	public String commMain(Model model,CommSearchDto commSearchDto,@PathVariable("page") Optional<Integer> page,MainCommDto adminMainCommDto) {
		Pageable pageable= PageRequest.of(page.isPresent()? page.get() : 0, 10);
		Page<MainCommDto> comms = commSearchService.getmainCommPage(commSearchDto, adminMainCommDto ,pageable);
		// view에서 쓸 수 있도록 model.addAttribute 작성
		model.addAttribute("comms", comms);
		model.addAttribute("commSearchDto",commSearchDto);
		model.addAttribute("maxPage",5);

		return "";
	}
	

}
