package br.com.nn.accounting_service.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.RoleChangeForm;
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
		if (id.get().isEmpty()) {
			this.addRole(user, Group.ADMIN.getRole());;
		} else {
			this.addRole(user, Group.USER.getRole());
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
		Set<User> users = userRepository.findAllUsers();
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
		return user.getRoles().contains("ROLE_ADMINISTRATOR");
	}

	public UserView changeRole(@Valid RoleChangeForm roleChangeForm) {
		StringBuilder builder = new StringBuilder();
		builder.append("ROLE_");
		builder.append(roleChangeForm.getRole());
		String email = roleChangeForm.getUser();
		String role = builder.toString();
		User user = userRepository
			.findWithEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
		if (roleChangeForm.getOperation().equals("GRANT")) {
			this.addRole(user, role);
		} else {
			this.removeRole(user, role);
		}
		return new UserView(user);
	}

	private void addRole(User user, String role) {
		PrincipleGroup group = groupRepository
				.findByName(role)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
		List<String> roles = user.getRoles();
		if (roles.contains(role)) {
			throw new BadRequestException("The user already has this role!");
		}
		
		if (isAdmin(user)) {
			throw new BadRequestException("The user cannot combine administrative and business roles!");
		}
		if (role.equals(Group.ADMIN.getRole()) && roles.size() > 0) {
			throw new BadRequestException("The user cannot combine administrative and business roles!");
		}
		
		user.addUserGroups(group);
		userRepository.save(user);
		
	}
	
	private void removeRole(User user, String role) {
		PrincipleGroup group = groupRepository
				.findByName(role)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
		List<String> roles = user.getRoles();
		if (!roles.contains(role)) {
			throw new BadRequestException("The user does not have a role!");
		}
		if (role.equals("ROLE_ADMINISTRATOR")) {
			throw new BadRequestException("Can't remove ADMINISTRATOR role!");
		}
		if (roles.size() == 1) {
			throw new BadRequestException("The user must have at least one role!");
		}
		user.getUserGroups().remove(group);
		userRepository.save(user);
	}
}
