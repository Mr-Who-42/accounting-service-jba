package br.com.nn.accounting_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.UserForm;
import br.com.nn.accounting_service.dto.UserView;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.exception.ResourceNotFoundException;
import br.com.nn.accounting_service.model.PrincipleGroup;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.PrincipleGroupRepository;
import br.com.nn.accounting_service.repository.UserRepository;
import br.com.nn.accounting_service.util.Group;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PrincipleGroupRepository groupRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public UserView registerUser(UserForm userForm) {
		if (userRepository.existsByEmailIgnoreCase(userForm.getEmail())) {
			throw new BadRequestException("User exist!");
		}
		User user = new User(userForm, passwordEncoder.encode(userForm.getPassword()));
		Optional<List<Long>> id = userRepository.findAny(PageRequest.of(0, 1));
		Optional<PrincipleGroup> adminGroup = groupRepository.findById(Group.ADMIN.getId());
		if (id.get().isEmpty()) {
			user.addUserGroups(adminGroup.get());;
		} else {
			user.addUserGroups(groupRepository
					.findById(Group.USER.getId())
					.get());
		}
		try {			
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("User exist!");
		}
		return new UserView(user);
	}
	
	public void changePass(UserDetailsImpl userDetails, String password) {
		
		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadRequestException("The passwords must be diffent!");
		}
		userRepository
				.updatePasswordByEmail(userDetails.getUsername(), passwordEncoder.encode(password));
	}

	public List<UserView> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(UserView::new).toList();
	}

	public void deleteUser(String email) {
		User user = userRepository
			.findByEmailIgnoreCase(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
		if (isAdmin(user)) {
			throw new BadRequestException("Can't remove ADMINISTRATOR role!");
		}
		userRepository.delete(user);
		
	}
	
	private boolean isAdmin(User user) {
		return user.getUserGroups().stream().findAny().get().getName().equals("ROLE_ADMINISTRATOR");
	}
	
}
