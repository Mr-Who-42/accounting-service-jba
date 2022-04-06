package br.com.nn.accounting_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.UserPayrollView;
import br.com.nn.accounting_service.service.PayrollService;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {
	
	@Autowired
	PayrollService payrollService;
	
	@GetMapping("/payment")
	public ResponseEntity<?> getPayment(@AuthenticationPrincipal UserDetailsImpl details,
			@RequestParam(required = false) String period) {

		if (period == null) {
			List<UserPayrollView> response = payrollService.getAllPayrolls(details.getUserId());
			
			return ResponseEntity.ok(response);
		} else {
			UserPayrollView response = payrollService.getPayroll(details.getUserId(), period);
			return ResponseEntity.ok(response);
			
		}
	}
}
