package net.kaczmarzyk.jpacert.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;


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
	
	@ManyToOne
	private Branch branch;
	
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
	
	public void setPhoneNumber(PhoneType type, String number) {
		if (phoneNumbers == null) {
			phoneNumbers = new HashMap<>();
		}
		phoneNumbers.put(type, number);
	}
	
	public String getPhoneNumber(PhoneType type) {
		return phoneNumbers != null ? phoneNumbers.get(type) : null;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	public Branch getBranch() {
		return branch;
	}
}
