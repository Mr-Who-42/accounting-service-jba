package br.com.nn.accounting_service.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.com.nn.accounting_service.model.PrincipleGroup;

public class UserView {
	private long id;
	private String name;
	private String lastname;
	private String email;
	private List<String> roles;
	
	
	public UserView() {
	}

	public UserView(long id, String name, String lastname, String email, Set<PrincipleGroup> roles) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.roles = roles.stream().map(PrincipleGroup::getName).sorted().toList();
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
