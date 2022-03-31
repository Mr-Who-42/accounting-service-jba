package br.com.nn.accounting_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.model.Payroll;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.PayrollRepository;
import br.com.nn.accounting_service.repository.UserRepository;

@RestController
@RequestMapping("/api/acct")
@Validated
public class AccountingController {
	
	@Autowired
	PayrollRepository payrollRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/payments")
	@Transactional
	public Map<String, String> postPayroll(@RequestBody List<@Valid PayrollForm> payrolls) {
		Map<String, List<@Valid PayrollForm>> payrollsGrouped = payrolls.stream().collect(Collectors.groupingBy(PayrollForm::getEmployee));
		payrollsGrouped.forEach(
				(email,payrollsList)-> {
					User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(
							() -> new BadRequestException("Employee not found!"));
					payrollsList.forEach(payroll -> {
						try {
							payrollRepository.save(new Payroll(payroll, user));
						} catch (DataIntegrityViolationException e) {
							throw new BadRequestException("User: " + user.getEmail() +
									 "already has payroll for " + payroll.getPeriod());
						}
						
					});
				});
		Map<String, String> response = new HashMap<>();
		response.put("status","Added successfully");
		return response;
	}
}
