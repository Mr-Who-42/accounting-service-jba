package br.com.nn.accounting_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserView registerUser(UserForm userForm) {
		User user = new User(userForm);
		userRepository.save(user);
		return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail());
	}
}
