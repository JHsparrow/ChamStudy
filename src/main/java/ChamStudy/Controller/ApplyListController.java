package ChamStudy.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ChamStudy.Dto.ApplyListDto;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Service.ApplyListService;
import ChamStudy.Service.ClassInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplyListController {
	
	private final ClassInfoService classInfoService;
	private final ApplyListService applyListService;
	
	@GetMapping(value="/applyList")
	public String applyListView() { //회원 수강 리스트
		

		return "/MainForm/myclass/index";
	}
	
	@PostMapping(value="/applyList")
	public @ResponseBody ResponseEntity applyList(@RequestBody ApplyListDto applyListDto, BindingResult bindingResult, Principal principal, Authentication authentication) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		//Authentication 객체가 null 이면 세션에 값이 없다 (=로그인하지 않았다)
		//401에러
		if (authentication == null) {
			return new ResponseEntity<String>("로그인 후 이용해 주세요", HttpStatus.UNAUTHORIZED );
		}
		
		UserInfo session = (UserInfo) authentication.getPrincipal();
		
		//String email = principal.getName();
		
		Long applyListId = (long) -1;
		
		try {
			applyListId = applyListService.addClass(applyListDto, session.getEmail());
			
			return new ResponseEntity<Long>(applyListId, HttpStatus.OK );
		} catch(Exception e) {
			applyListId = (long) -9;
			e.printStackTrace();
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
