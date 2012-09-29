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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Branch {

	@Id @GeneratedValue
	private Long id;
	
	@ElementCollection
	@CollectionTable(
		name="branch_addresses",
		joinColumns={@JoinColumn(name="company_id")}
	)
	@AttributeOverrides({
		@AttributeOverride(name="zip", column=@Column(name="postal_code"))
	})
	private Collection<Address> addresses;
	
	@OneToMany(mappedBy="branch")
	private Collection<Employee> employees;
	
	@ManyToOne
	private Company company;
	
	
	Branch() {
	}
	
	public Branch(Address address, Address... addresses) {
		this.addresses = new ArrayList<>();
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
			addresses = new ArrayList<>();
		}
		addresses.add(address);
	}
	
	public void addEmployee(Employee e) {
		if (employees == null) {
			employees = new ArrayList<>();
		}
		employees.add(e);
		e.setBranch(this);
	}
	
	public Collection<Employee> getEmployees() {
		return employees;
	}

	void setCompany(Company company) {
		this.company = company;
	}
}
