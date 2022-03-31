package br.com.nn.accounting_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nn.accounting_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmailIgnoreCase(String email);
	
	Optional<User> findByEmailIgnoreCase(String email);
	
	@Query("Select u from User u JOIN FETCH u.userGroups where UPPER(u.email)=UPPER(?1)")
	Optional<User> findWithEmail(String email);
	
	@Query("Select u.password from User u where UPPER(u.email)=UPPER(?1)")
	String getPasswordByEmail(String email);
	
	@Modifying
	@Query("Update User u set u.password = ?2 where UPPER(u.email)=UPPER(?1)")
	void updatePasswordByEmail(String email, String password);
	
	@Query("Select u.id from User u")
	Optional<List<Long>> findAny(Pageable pageable);
	
	@Query("Select u.id from User u where UPPER(u.email)=UPPER(?1)")
	Optional<Long> findId(String email);
}
