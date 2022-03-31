package br.com.nn.accounting_service.dto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PayrollForm {
	
	@NotBlank(message="Email must not be blank!")
	@Email(regexp=".*@acme\\.com$", message="Must be valid email from ACME!")
	private String employee;
	//do validation 
	private LocalDate period;
	@Min(value = 0, message = "Must be non negative!")
	private long salary;
	
	
	public PayrollForm() {
	}
	//period string 'MM-yyyy'
	public PayrollForm(String employee, String period, @Min(value = 0, message = "Must be non negative!") long salary) {
		this.employee = employee.toLowerCase();
		this.period = YearMonth
				.parse(period, DateTimeFormatter.ofPattern("MM-yyy")).atDay(1);
		this.salary = salary;
	}
	
	
	public LocalDate formatPeriod(String period) {
		LocalDate periodDate = LocalDate.now();
		try {
			periodDate = YearMonth
			.parse(period, DateTimeFormatter.ofPattern("MM-yyy")).atDay(1);
			if (periodDate.isAfter(YearMonth.now().atDay(1))) {
				System.out.println(period + "isnt a valid period");
			}
		} catch (DateTimeParseException e) {
			System.out.println(period + "isnt a valid period");
	
		}
		return periodDate;
	}
	
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee.toLowerCase();
	}
	public LocalDate getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = YearMonth
				.parse(period, DateTimeFormatter.ofPattern("MM-yyy")).atDay(1);
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}		
}
