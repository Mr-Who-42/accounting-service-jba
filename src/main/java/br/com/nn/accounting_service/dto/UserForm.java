package br.com.nn.accounting_service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserForm {
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastname;
	@Email
	private String email;
	@NotEmpty
	private String password;
	
	public UserForm() {
	}

	public UserForm(@NotEmpty String name, @NotEmpty String lastname, @Email String email, @NotEmpty String password) {
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}