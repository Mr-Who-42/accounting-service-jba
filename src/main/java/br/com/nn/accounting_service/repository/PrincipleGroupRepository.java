package br.com.nn.accounting_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nn.accounting_service.model.PrincipleGroup;

public interface PrincipleGroupRepository extends JpaRepository<PrincipleGroup, Long> {
	
}
