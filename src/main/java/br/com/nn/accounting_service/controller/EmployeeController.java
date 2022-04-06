package br.com.nn.accounting_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nn.accounting_service.config.security.authentication.UserDetailsImpl;
import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.dto.UserPayrollView;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.model.Payroll;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.PayrollRepository;
import br.com.nn.accounting_service.repository.UserRepository;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PayrollRepository payrollRepository;
	
	@GetMapping("/payment")
	public ResponseEntity<?> getPayment(@AuthenticationPrincipal UserDetailsImpl details,
			@RequestParam(required = false) String period) {
		User user = userRepository.findByEmailIgnoreCase(details.getUsername()).get();
		if (period == null) {
			List<UserPayrollView> payrolls = user.getPayrolls().stream().map(payroll -> new UserPayrollView(user.getName(),
					user.getLastname(), payroll.getPeriod(), payroll.getSalary())).toList();
			return ResponseEntity.ok(payrolls);
		} else {
			LocalDate periodFormated = new PayrollForm().formatPeriod(period);
			try {
				Payroll payroll = payrollRepository
						.findByUserIdAndPeriod(user.getId(), periodFormated)
						.orElseThrow(() -> new BadRequestException("Period Invalid"));
				return ResponseEntity.ok(new UserPayrollView(
						user.getName(),
						user.getLastname(),
						payroll.getPeriod(),
						payroll.getSalary()));
			} catch (Exception e) {
				throw new BadRequestException("Period Invalid");
			}
			
		}
	}
}
