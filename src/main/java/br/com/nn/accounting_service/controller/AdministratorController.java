package br.com.nn.accounting_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.RoleChangeForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	public List<UserView> getUsers() {
		
		List<UserView> response = userService.getAllUsers();
		return response;
	}
	
	@DeleteMapping("/user/{email}")
	public Map<String, String> deleteUser(@PathVariable String email){
		userService.deleteUser(email);
		
		Map<String, String> response = new HashMap<>();
        response.put("user", email);
        response.put("status", "Deleted successfully!");
        return response;
	}
	
	@PutMapping("/user/role")
	public UserView changeRoleOfUser(@RequestBody @Valid RoleChangeForm roleChangeForm) {
		return userService.changeRole(roleChangeForm);
	}
}
