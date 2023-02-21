package ChamStudy.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ChamStudy.Service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	UserService userService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//로그인에 대한 설정
		http.formLogin()
		    .loginPage("/users/signIn") //로그인 페이지 url설정
			.defaultSuccessUrl("/") //로그인 성공시 이동할 페이지
			.usernameParameter("email") //로그인시 사용할 파라메터 이름
			.failureUrl("/users/signIn/error") //로그인 실패시 이동할 url
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/users/logout")) //로그아웃 url
			.logoutSuccessUrl("/"); //로그아웃 성공시 이동할 url
		
		/**
		 * post 방식 호출 시 에러 발생 (status : 302, 401, 403)
		 * 아래 옵션을 사용하여 csrf(Cross-Site Request Forgery) 비활성화
		 */
		http.csrf().disable();
		
		//페이지의 접근에 관한 설정
		http.authorizeRequests()
		    .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
		    .mvcMatchers("/", "/users/**", "/adminForm/**","/contents/**" , "/images/**", "/adminClass", "/adminCategory/**", "/cs/**", "/adminOnClass/**").permitAll() //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
		    .mvcMatchers("/admin/**").hasRole("ADMIN") // '/admin' 으로 시작하는 경로는 계정이 ADMIN role일 경우에만 접근 가능하도록 설정
		    .mvcMatchers("/seller/**").hasRole("SELLER") // '/admin' 으로 시작하는 경로는 계정이 ADMIN role일 경우에만 접근 가능하도록 설정
		    .anyRequest().authenticated(); //그 외에 페이지는 모두 로그인(인증)을 받아야 한다.
		
		//인증되지 않은 사용자가 리소스(페이지, 이미지 등..)에 접근했을때 설정
		//http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		//
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		
		return http.build();
	}
	
	//비밀번호를 암호화 해서 저장
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}