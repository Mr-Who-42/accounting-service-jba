package br.com.nn.accounting_service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserPayrollView {
	private String name;
	private String lastname;
	private String period;
	private long salary;
	
	
	public UserPayrollView() {
	}
	
	public UserPayrollView(String name, String lastname, LocalDate period, long salary) {
		this.name = name;
		this.lastname = lastname;
		this.period = period.format(DateTimeFormatter.ofPattern("MM-yyyy"));
		this.salary = salary;
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	
}
