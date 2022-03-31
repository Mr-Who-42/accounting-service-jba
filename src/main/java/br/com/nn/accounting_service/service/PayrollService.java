package br.com.nn.accounting_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.model.Payroll;
import br.com.nn.accounting_service.repository.PayrollRepository;
import br.com.nn.accounting_service.repository.UserRepository;

@Service
public class PayrollService {
	@Autowired
	PayrollRepository payrollRepository;
	
	@Autowired
	UserRepository userRepository;

	public Map<String, String> registerPayrolls(List<PayrollForm> payrolls) {
		Map<String, List<PayrollForm>> payrollsGrouped = this.groupPayrollsByEmail(payrolls);
		
		payrollsGrouped.forEach(
				(email,payrollsList)-> {
					long user = userRepository.findId(email).orElseThrow(
							() -> new BadRequestException("Employee: " + email + " not found!"));
					payrollsList.forEach(payroll -> this.savePayroll(payroll, user, email)
						);
				});
		Map<String, String> response = new HashMap<>();
		response.put("status","Added successfully");
		return response;
	}
	
	private Map<String, List<PayrollForm>> groupPayrollsByEmail(List<PayrollForm> payrolls) {
		Map<String, List<PayrollForm>> payrollsGrouped = payrolls
				.stream()
				.collect(Collectors.groupingBy(PayrollForm::getEmployee));
		
		return payrollsGrouped;
	}
	
	private void savePayroll(PayrollForm payroll, long userId, String email) {
		try {
			payrollRepository.save(new Payroll(payroll, userId));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("User: " + email +
					 "already has payroll for " + payroll.getPeriod());
		}
	}
	
}
