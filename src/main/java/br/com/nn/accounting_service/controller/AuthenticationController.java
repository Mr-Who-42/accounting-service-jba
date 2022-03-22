package br.com.nn.accounting_service.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/signup")
	public UserView registerUser(@Valid @RequestBody UserForm userForm) {
		User user = new User(userForm);
		userRepository.save(user);
		return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail());
	}
}
