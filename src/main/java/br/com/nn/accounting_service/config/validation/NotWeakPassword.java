package br.com.nn.accounting_service.config.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotWeakPasswordValidator.class)
public @interface NotWeakPassword {
	
	String message() default "The password is in the hacker's database!";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
