package br.com.nn.accounting_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.nn.accounting_service.dto.PayrollForm;
import br.com.nn.accounting_service.model.Payroll;
import br.com.nn.accounting_service.model.User;
import br.com.nn.accounting_service.repository.PayrollRepository;
import br.com.nn.accounting_service.repository.UserRepository;

class PayrollServiceTest {
	
	private PayrollService payrollService;
	@Mock
	private PayrollRepository payrollRepository;
	@Mock
	private UserRepository userRepository;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.payrollService = 
				new PayrollService(this.payrollRepository, this.userRepository);
	}
	
	@Test
	void shouldRegisterPayroll() {
		Mockito.when(userRepository.findId("admin@acme.com"))
		.thenReturn(Optional.of(1L));
		Mockito.when(userRepository.findId("user@acme.com"))
		.thenReturn(Optional.of(2L));
		Mockito.when(userRepository.findId("test@acme.com"))
		.thenReturn(Optional.of(3L));
		Map<String, String> response = payrollService.registerPayrolls(payrollForms());
		assertEquals("Added successfully", response.get("status"));
		Mockito.verify(userRepository,times(3)).findId(anyString());
		Mockito.verify(payrollRepository,times(5)).save(isA(Payroll.class));
	}
	
	private List<PayrollForm> payrollForms() {
		List<PayrollForm> payrollList = new ArrayList<>();
		payrollList.add(new PayrollForm("admin@acme.com", "02-2021", 111));
		payrollList.add(new PayrollForm("user@acme.com", "02-2021", 222));
		payrollList.add(new PayrollForm("user@acme.com", "03-2021", 245));
		payrollList.add(new PayrollForm("user@acme.com", "04-2021", 333));
		payrollList.add(new PayrollForm("test@acme.com", "02-2021", 51));
		return payrollList;
	}
}
