package br.com.nn.accounting_service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.nn.accounting_service.config.validation.NotWeakPassword;

public class UserForm {
	@NotBlank(message="Name must not be blank!")
	private String name;
	@NotBlank(message="Lastname must not be blank!")
	private String lastname;
	@NotBlank(message="Email must not be blank!")
	@Email(regexp=".*@acme\\.com$", message="Must be valid email from ACME!")
	private String email;
	@NotBlank
	@Size(min=12, message="Password lenght must be 12 chars minimum!")
	@NotWeakPassword
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
