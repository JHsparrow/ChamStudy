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
		return "AdminForm/pages-profile";
	}
	
	@GetMapping(value = "/signin")
	public String signin() {
		return "AdminForm/pages-sign-in";
	}
	
	@GetMapping(value = "/signup")
	public String signup() {
		return "AdminForm/pages-sign-up";
	}
	
	@GetMapping(value = "/blank")
	public String blank() {
		return "AdminForm/pages-blank";
	}
	
	@GetMapping(value = "/button")
	public String button() {
		return "AdminForm/ui-buttons";
	}
	
	@GetMapping(value = "/forms")
	public String forms() {
		return "AdminForm/ui-forms";
	}
	
	@GetMapping(value = "/cards")
	public String cards() {
		return "AdminForm/ui-cards";
	}
	
	@GetMapping(value = "/typography")
	public String typography() {
		return "AdminForm/ui-typography";
	}
	
	@GetMapping(value = "/icons")
	public String icons() {
		return "AdminForm/icons-feather";
	}
	
	@GetMapping(value = "/chart")
	public String chart() {
		return "AdminForm/charts-chartjs";
	}
	
	@GetMapping(value = "/maps")
	public String maps() {
		return "AdminForm/maps-google";
	}
	
	
	
}

