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

import ChamStudy.Service.PrincipalOauth2UserService;
import ChamStudy.Service.UserService;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext.AND;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	UserService userService;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
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
			.logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
			.and()
			//코드 받기(인증), 액세스 토큰(권한), 사용자 프로필 정보를 가져오고
			//그 정보를 토대로 회원가입 하기
			//즉 인증을 받은 정보를 가져와서 내가 만든 엔티티에 넣어주기
			//tip.로그인이 완료가 되면 코드를 받는게 아니라
			//토큰과 사용자 프로필 정보만 따로 받는다.
			.oauth2Login() //구글 로그인 설정
			.userInfoEndpoint()
			.userService(principalOauth2UserService); //값이 없거나 이상하면 오류가 뜸
		
		/*/구글 로그인 설정 파일 참고용/
		 * http .csrf().disable() .headers().frameOptions().disable() .and()
		 * .authorizeRequests() .anyRequest().authenticated() .and() .logout()
		 * .logoutSuccessUrl("/") .and() .oauth2Login() .userInfoEndpoint()
		 * .userService(customOAuth2UserService);
		 */
		
		/**
		 * post 방식 호출 시 에러 발생 (status : 302, 401, 403)
		 * 아래 옵션을 사용하여 csrf(Cross-Site Request Forgery) 비활성화
		 */
		http.csrf().disable();
		

		
		//페이지의 접근에 관한 설정
		http.authorizeRequests()
		    .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
<<<<<<< HEAD
		    .mvcMatchers("/", "/users/**", "/adminForm/**","/contents/**" , "/images/**", "/adminClass", "/cs/**", "/adminOnClass/**", "/oauth2**").permitAll() //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
=======
		    .mvcMatchers("/", "/users/**", "/adminForm/**","/contents/**" , "/images/**", "/adminClass", "/adminCategory/**", "/cs/**", "/adminOnClass/**").permitAll() //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
>>>>>>> f0e20c338b63012f156352ac56eefd958ec8d804
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