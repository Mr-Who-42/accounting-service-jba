package br.com.nn.accounting_service.config.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotWeakPasswordValidator implements ConstraintValidator<NotWeakPassword, String>{

	private List<String> breachedPasswords = new ArrayList<>(List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"));

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (breachedPasswords.contains(value)) {
			return false;
		}
		return true;
	}

}
