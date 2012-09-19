package net.kaczmarzyk.jpacert.customer.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Customer {

	@Id @GeneratedValue
	private Long id;
	
	private String firstname;
	
	private String lastname;

	@OneToMany(mappedBy="customer")
	private Collection<Order> orders;
	
	
	Customer() {
	}
	
	public Customer(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

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
	
}
