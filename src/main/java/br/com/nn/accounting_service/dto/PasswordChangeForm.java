package br.com.nn.accounting_service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.nn.accounting_service.config.validation.NotWeakPassword;

public class PasswordChangeForm {
	
	@NotBlank
	@Size(min=12, message="Password lenght must be 12 chars minimum!")
	@NotWeakPassword
	@JsonAlias("new_password")
	String newPassword;

	public PasswordChangeForm() {
	}
	
	public PasswordChangeForm(
			@NotBlank @Size(min = 12, message = "Password lenght must be 12 chars minimum!") String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
