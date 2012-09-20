package net.kaczmarzyk.jpacert.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Company {

	@Id @GeneratedValue
	private Long id;
	
	@AttributeOverrides({
		@AttributeOverride(name="zip", column=@Column(name="postal_code"))
	})
	private Address address;
	
	private String name;

	
	Company() {
	}
	
	public Company(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
}
