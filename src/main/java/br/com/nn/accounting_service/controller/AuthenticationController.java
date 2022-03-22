package br.com.nn.accounting_service.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public UserView registerUser(@Valid @RequestBody UserForm userForm) {
		return userService.registerUser(userForm);
	}
}
