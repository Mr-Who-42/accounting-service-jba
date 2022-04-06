package br.com.nn.accounting_service.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.dto.UserPayrollView;
import br.com.nn.accounting_service.exception.AccessForbiddenException;
import br.com.nn.accounting_service.exception.BadRequestException;
import br.com.nn.accounting_service.model.Payroll;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.PayrollRepository;
import br.com.nn.accounting_service.repository.UserRepository;

@Service
public class PayrollService {

	PayrollRepository payrollRepository;
	UserRepository userRepository;
	
	@Autowired
	public PayrollService(PayrollRepository payrollRepository, UserRepository userRepository) {
		this.payrollRepository = payrollRepository;
		this.userRepository = userRepository;
	}

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
	
	public Map<String, String> alterPayroll(PayrollForm payrollForm) {
		Optional<Long> userId = userRepository.findId(payrollForm.getEmployee());
		Long id = userId.orElseThrow(() -> 
						new BadRequestException("Employee: " +
						                        payrollForm.getEmployee() +
						                        " not found!"));
		 Optional<Payroll> optionalPayroll = payrollRepository
				.findByUserIdAndPeriod(id, payrollForm.getPeriod());
		 Payroll payroll = optionalPayroll.orElseThrow(() -> 
				 new BadRequestException("Period not found!"));
		 payroll.setSalary(payrollForm.getSalary());
		 payrollRepository.save(payroll);
		 Map<String, String> response = new HashMap<>();
		 response.put("status","Updated successfully");
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

	public UserPayrollView getPayroll(long userId, String period) {
		LocalDate periodFormated = new PayrollForm().formatPeriod(period);
		Payroll payroll = payrollRepository
								.findByUserIdAndPeriod(userId, periodFormated)
								.orElseThrow(() -> new BadRequestException("Period Invalid"));
		User user = payroll.getUser();
		return new UserPayrollView(user.getName(),
				            user.getLastname(),
				            payroll.getPeriod(),
				            payroll.getSalary());
		
	}

	public List<UserPayrollView> getAllPayrolls(long userId) {
		
		User user = userRepository.findById(userId)
				                  .orElseThrow(() -> new AccessForbiddenException());
		List<UserPayrollView> payrolls = user.getPayrolls()
				.stream().map(payroll -> new UserPayrollView(user.getName(),
						user.getLastname(), payroll.getPeriod(), payroll.getSalary()))
				.toList();
		return payrolls;
		
		
	}
	
}
