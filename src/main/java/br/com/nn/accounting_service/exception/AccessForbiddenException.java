package br.com.nn.accounting_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessForbiddenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

}
