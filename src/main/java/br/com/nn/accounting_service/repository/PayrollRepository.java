package br.com.nn.accounting_service.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nn.accounting_service.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
	Payroll findByUserIdAndPeriod(Long userId, LocalDate period);
}
