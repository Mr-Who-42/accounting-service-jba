package br.com.nn.accounting_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nn.accounting_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmailIgnoreCase(String email);
	
	User findByEmailIgnoreCase(String email);
	@Query("Select u.password from User u where UPPER(u.email)=UPPER(?1)")
	String getPasswordByEmail(String email);
}
