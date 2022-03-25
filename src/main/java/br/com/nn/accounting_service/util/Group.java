package br.com.nn.accounting_service.util;

public enum Group {
	ADMIN(1L, "ROLE_ADMINISTRATOR"),
	USER(2L, "ROLE_USER"),
	ACCOUNTANT(3L, "ROLE_ACCOUNTANT"),
	ROLE_AUDITOR(4L, "ROLE AUDITOR");
	
	private final long id;
	private final String role;
	
	Group(long id, String role) {
		this.id = id;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public String getRole() {
		return role;
	}
	
}
