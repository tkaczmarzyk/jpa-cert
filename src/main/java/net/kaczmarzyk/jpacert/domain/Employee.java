package net.kaczmarzyk.jpacert.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;


@Entity
public class Employee {

	@Id @GeneratedValue
	private Long id;
	
	private String lastname;
	
	@ElementCollection
	@CollectionTable(name="employee_phones")
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name="phone_type")
	@Column(name="phone_number")
	private Map<PhoneType, String> phoneNumbers;
	
	@OneToMany(mappedBy="employee")
	private Collection<Certificate> certificates;
	
	@Column(scale=2)
	private BigDecimal salary;
	
	
	Employee() {
	}
	
	public Employee(String lastname, BigDecimal salary) {
		this.lastname = lastname;
		this.salary = salary;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setPhoneNumber(PhoneType type, String number) {
		if (phoneNumbers == null) {
			phoneNumbers = new HashMap<>();
		}
		phoneNumbers.put(type, number);
	}
	
	public String getPhoneNumber(PhoneType type) {
		return phoneNumbers != null ? phoneNumbers.get(type) : null;
	}

	public BigDecimal getSalary() {
		return salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	public Collection<Certificate> getCertificates() {
		if (certificates == null) {
			certificates = new ArrayList<>();
		}
		return certificates;
	}
}
