package br.com.nn.accounting_service.config.validation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.nn.accounting_service.dto.ValidationErrorView;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ValidationErrorView handle(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		List<String> message = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(e->message.add(e.getDefaultMessage()));
		String path = request.getRequestURI();
		return new ValidationErrorView(HttpStatus.BAD_REQUEST, message, path);
	}
}
