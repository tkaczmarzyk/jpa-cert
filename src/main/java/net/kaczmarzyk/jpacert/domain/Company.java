package net.kaczmarzyk.jpacert.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Company {

	@Id @GeneratedValue
	private Long id;
	
	@ElementCollection
	@CollectionTable(
		name="company_addresses",
		joinColumns={@JoinColumn(name="company_id")}
	)
	@AttributeOverrides({
		@AttributeOverride(name="zip", column=@Column(name="postal_code"))
	})
	private Collection<Address> addresses;
	
	private String name;

	@OneToMany(mappedBy="company")
	private Collection<Employee> employees;
	
	
	Company() {
	}
	
	public Company(String name, Address address, Address... addresses) {
		this.name = name;
		this.addresses = new ArrayList<Address>();
		this.addresses.add(address);
		if (addresses != null) {
			this.addresses.addAll(Arrays.asList(addresses));
		}
	}
	
	public Collection<Address> getAddress() {
		return addresses;
	}

	public void addAddress(Address address) {
		if (addresses == null) {
			addresses = new ArrayList<Address>();
		}
		addresses.add(address);
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
	
	public void addEmployee(Employee e) {
		if (employees == null) {
			employees = new ArrayList<Employee>();
		}
		employees.add(e);
		e.setCompany(this);
	}
	
	public Collection<Employee> getEmployees() {
		return employees;
	}
}
