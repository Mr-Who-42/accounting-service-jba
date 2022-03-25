package br.com.nn.accounting_service.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ValidationErrorView {
	private LocalDateTime timestamp;
	private HttpStatus status;
	private String error;
	private List<String> message;
	private String path;
	
	public ValidationErrorView(HttpStatus status,List<String> message, String path) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.error = status.getReasonPhrase();
		this.message = message;
		this.path = path;
	}

	public String getTimestamp() {
		
		return timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status.value();
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getMessage() {
		return Collections.unmodifiableList(message);
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
			
}
