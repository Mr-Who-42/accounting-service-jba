package br.com.nn.accounting_service.controller;

import java.util.HashMap;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.PasswordChangeForm;
import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	@Transactional
	public UserView registerUser(@Valid @RequestBody UserForm userForm) {
		return userService.registerUser(userForm);
	}
	
	@PostMapping("/changepass")
	@Transactional
	public HashMap<String, String> changePassword(
			@Valid @RequestBody PasswordChangeForm passChangeForm,
			@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		userService.changePass(userDetails, passChangeForm.getNewPassword());
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("email", userDetails.getUsername());
		response.put("status", "password has been updated successfully!");
		return response;
	}
}
