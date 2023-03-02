package ChamStudy.Controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ChamStudy.Service.AdminMainService;
import ChamStudy.Service.HomeMainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeMainController {
	
	private final HomeMainService homeMainService;

	@GetMapping(value = "/")
	public String main() {
		homeMainService.addCount();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 로그인하지 않은 사용자
            return "main";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 관리자 권한을 가진 사용자
            return "index";
        } else {
            // 일반 사용자
            return "main";
        }
		
		
		
		
	}
}
