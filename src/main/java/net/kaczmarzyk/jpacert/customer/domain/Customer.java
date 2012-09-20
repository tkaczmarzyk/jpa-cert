package net.kaczmarzyk.jpacert.customer.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Access(AccessType.FIELD)
public class Customer {

	@TableGenerator(name="cust_gen") // generator may be defined on any property or class
	@Id @GeneratedValue(generator="cust_gen")
	private Long id;
	
	private String firstname;
	
	@Transient // transient because property access is used
	private String lastname;

	@OneToMany(mappedBy="customer")
	private Collection<Order> orders;
	
	@Temporal(TemporalType.TIMESTAMP) // java.sql.(Date/Time/Timestamp) - no @Temporal required
	private Date creationDate;
	
	@Embedded // optional
	private Address address;
	
	Customer() {
	}
	
	public Customer(String firstname, String lastname, Address address) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.creationDate = new Date();
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Access(AccessType.PROPERTY) // overridden access in the entity. The field must be transient to prevent default mapping being applied 
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void addOrder(Order order) {
		if (orders == null) {
			orders = new ArrayList<Order>();
		}
		orders.add(order);
		order.setCustomer(this);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fullname=" + firstname + " " + lastname + "]";
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
