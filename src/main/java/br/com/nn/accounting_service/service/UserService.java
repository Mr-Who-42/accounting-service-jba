package br.com.nn.accounting_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public UserView registerUser(UserForm userForm) {
		if (userRepository.existsByEmailIgnoreCase(userForm.getEmail())) {
			throw new BadRequestException("User exist!");
		}
			
		User user = new User(userForm, passwordEncoder.encode(userForm.getPassword()));
		userRepository.save(user);
		return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail());
	}
	
	public void changePass(UserDetailsImpl userDetails, String password) {
		
		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadRequestException("The passwords must be diffent!");
		}
		User user = userRepository.findByEmailIgnoreCase(userDetails.getUsername());
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
	
}
