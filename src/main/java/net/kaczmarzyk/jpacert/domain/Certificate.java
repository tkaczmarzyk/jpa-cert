package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Certificate {

	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private Employee employee;
	
	
	Certificate() {
	}
	
	public Certificate(String name) {
		this.name = name;
	}

	public Employee getEmployee() {
		return employee;
	}

	void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
