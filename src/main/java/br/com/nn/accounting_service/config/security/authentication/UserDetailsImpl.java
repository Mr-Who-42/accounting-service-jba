package br.com.nn.accounting_service.config.security.authentication;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.nn.accounting_service.model.User;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	private final String username;
	private final String password;
	private final boolean enabled;
	private final List<GrantedAuthority> rolesAndAuthorities;
	
	public UserDetailsImpl(User user) {
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
		this.rolesAndAuthorities = user.getUserGroups().stream()
				.map(userGroup->userGroup.getName().toUpperCase())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
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
		return enabled;
	}

}
