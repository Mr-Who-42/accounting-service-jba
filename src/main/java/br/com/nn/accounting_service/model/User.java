package br.com.nn.accounting_service.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.nn.accounting_service.dto.UserForm;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@NotBlank
	private String name;
	@NotBlank
	private String lastname;
	@NotBlank
	@Column(unique = true)
	private String email;
	@NotBlank
	private String password;
	@OneToMany(mappedBy = "user")
	private List<Payroll> payrolls;
	
	@NotNull
	private boolean enabled = true;
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_groups",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<PrincipleGroup> userGroups = new HashSet<>();
	
	public User() {}
	
	public User(UserForm userForm, String password){
		this.name = userForm.getName();
		this.lastname = userForm.getLastname();
		this.email = userForm.getEmail();
		this.password = password;
	}
	
	public void addUserGroups(PrincipleGroup groups) {
		this.userGroups.add(groups);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}

	public Set<PrincipleGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<PrincipleGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<Payroll> getPayrolls() {
		return Collections.unmodifiableList(this.payrolls);
	}

	public void setPayrolls(List<Payroll> payrolls) {
		this.payrolls = payrolls;
	}
	
	public List<String> getRoles() {
		return userGroups.stream().map(PrincipleGroup::getName).sorted().toList();
	}
	
	
}
