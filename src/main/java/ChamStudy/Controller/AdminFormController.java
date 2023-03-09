package ChamStudy.Controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value="/adminForm")
@Controller
public class AdminFormController {
	
	@GetMapping(value = "/profile")
	public String profile() {
		return "adminForm/pages-profile";
	}
	
	@GetMapping(value = "/blank")
	public String blank() {
		return "adminForm/pages-blank";
	}
	
	@GetMapping(value = "/button")
	public String button() {
		return "adminForm/ui-buttons";
	}
	
	@GetMapping(value = "/forms")
	public String forms() {
		return "adminForm/ui-forms";
	}
	
	@GetMapping(value = "/cards")
	public String cards() {
		return "adminForm/ui-cards";
	}
	
	@GetMapping(value = "/typography")
	public String typography() {
		return "adminForm/ui-typography";
	}
	
	@GetMapping(value = "/icons")
	public String icons() {
		return "adminForm/icons-feather";
	}
	
	@GetMapping(value = "/list")
	public String list() {
		return "adminForm/pages-list";
	}
	
	
}

