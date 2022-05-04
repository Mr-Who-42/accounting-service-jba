package br.com.nn.accounting_service.dto;

import java.util.Collections;
import java.util.List;

import br.com.nn.accounting_service.model.User;

public class UserView {
	private long id;
	private String name;
	private String lastname;
	private String email;
	private List<String> roles;
	
	
	public UserView() {
	}
	public UserView(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.roles = user.getRoles();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return Collections.unmodifiableList(roles);
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
