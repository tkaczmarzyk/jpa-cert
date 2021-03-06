/**
 * This file is part of jpa-cert application.
 *
 * Jpa-cert is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jpa-cert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jpa-cert; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.kaczmarzyk.jpacert.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
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

	@OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
	@OrderBy("key.date DESC")
	private List<Order> orders;
	
	@Temporal(TemporalType.TIMESTAMP) // java.sql.(Date/Time/Timestamp) - no @Temporal required
	private Date creationDate;
	
	@ElementCollection
	@OrderColumn
	@Column(name="number")
	private List<String> telephoneNumbers;
	
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

	public List<Order> getOrders() {
		return orders;
	}

	public void addOrder(Order order) {
		if (orders == null) {
			orders = new ArrayList<>();
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

	public List<String> getTelephoneNumbers() {
		if (telephoneNumbers == null) {
			telephoneNumbers = new ArrayList<>();
		}
		return telephoneNumbers;
	}
}
