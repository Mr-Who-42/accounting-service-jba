package br.com.nn.accounting_service.config.security.authentication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findWithEmail(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		
		
		return user.map(UserDetailsImpl::new).get();		
	}

}
