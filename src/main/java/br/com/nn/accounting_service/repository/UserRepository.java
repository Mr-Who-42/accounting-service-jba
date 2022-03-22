package br.com.nn.accounting_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nn.accounting_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
