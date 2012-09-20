package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Employee {

	@Id @GeneratedValue
	private Long id;
	
	private String lastname;
	
	@ManyToOne
	private Company company;
	
	Employee() {
	}
	
	public Employee(String lastname) {
		this.lastname = lastname;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
