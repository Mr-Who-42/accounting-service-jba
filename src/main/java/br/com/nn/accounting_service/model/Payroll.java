package br.com.nn.accounting_service.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import br.com.nn.accounting_service.dto.PayrollForm;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UNIQUE_USER_PERIOD",columnNames = {"period", "user_id"}))
public class Payroll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",insertable = false, updatable = false)
	private User user;
	@Column(name = "user_id")
	private long userId;
	private LocalDate period;
	@Min(0)
	private long salary;
	
	public Payroll() {
	}

	public Payroll(PayrollForm payroll, long userId) {
		this.period = payroll.getPeriod();
		this.salary = payroll.getSalary();
		this.userId = userId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getPeriod() {
		return period;
	}
	public void setPeriod(LocalDate period) {
		this.period = period;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	
}
