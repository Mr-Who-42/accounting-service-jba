package br.com.nn.accounting_service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoleChangeForm {
	@NotBlank
	private String user;
	@NotBlank
	private String role;
	@NotBlank
	@Pattern(regexp = "GRANT|REMOVE", message = "Invalid operation!")
	private String operation;
	
	public RoleChangeForm() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
