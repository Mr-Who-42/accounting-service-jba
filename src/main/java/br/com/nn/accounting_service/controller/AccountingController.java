package br.com.nn.accounting_service.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.service.PayrollService;

@RestController
@RequestMapping("/api/acct")
@Validated
public class AccountingController {
	
	@Autowired
	PayrollService payrollService;
	
	@PostMapping("/payments")
	@Transactional
	public Map<String, String> postPayroll(@RequestBody List<@Valid PayrollForm> payrolls) {
		
		Map<String, String> response = payrollService.registerPayrolls(payrolls);
		return response;
	}
	
	@PutMapping("/payments")
	@Transactional
	public Map<String, String> putPayroll(@RequestBody @Valid PayrollForm payroll) {
		Map<String, String> response = payrollService.alterPayroll(payroll);
		return response;
		
	}
}
