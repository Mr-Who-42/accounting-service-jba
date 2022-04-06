package br.com.nn.accounting_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	public List<UserView> getUsers(){
		
		List<UserView> response = userService.getAllUsers();
		return response;
	}
	
}
