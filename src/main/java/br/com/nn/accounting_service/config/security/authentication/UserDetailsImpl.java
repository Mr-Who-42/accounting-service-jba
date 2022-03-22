package br.com.nn.accounting_service.config.security.authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	private final String username;
	private final String password;
	private final List<GrantedAuthority> rolesAndAuthorities;
	
	public UserDetailsImpl(String username, String password) {
		this.username = username;
		this.password = password;
		this.rolesAndAuthorities = List.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return rolesAndAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

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
		return true;
	}

}
