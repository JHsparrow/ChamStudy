package ChamStudy.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ChamStudy.Entity.UserInfo;

//시큐리티가 /signIn 주소 요청이 오면 낚아채서 로그인을 진행 시킨다.
//로그인을 진행이 완료과 되면 시큐리티 session을 만들어준다.
//오브젝트 타입 -> Authentication 타입 객체
//Authentication 안에 UserInfo 정보가 있어야 함.
//User오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication => UserDetails(PrincipalDetails)

public class PrincipalDetails implements UserDetails, OAuth2User{

	private UserInfo userInfo;
	private Map<String, Object> attribute;
	
	//일반 로그인
	public PrincipalDetails(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	//구글 로그인
	public PrincipalDetails(UserInfo userInfo, Map<String, Object> attribute) {
		this.userInfo = userInfo;
		this.attribute = attribute;
	}
	
	//UserInfo의 권한을 리턴 하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userInfo.getRole();
			}
		});
		return collect;
	}

	
	//패스워드를 리턴
	@Override
	public String getPassword() {
		return userInfo.getPassword();
	}

	//유저 이름을 리턴
	@Override
	public String getUsername() {
		return userInfo.getName();
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//비밀번호가
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//우리 사이트가 1년 동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함.
		//현재시간 - 로그인 시간 -> 1년을 초과하면 리턴을 false
		return false;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attribute;
	}

	@Override
	public String getName() {
		return null;
	}
	
	

}
