package net.kaczmarzyk.jpacert.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="orders")
public class Order {

	@Id @GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	private Customer customer;
	
	private String name;
	
	
	Order() {
	}
	
	public Order(String name, Date date) {
		this(name);
		this.date = date;
	}
	
	public Order(String name) {
		this.name = name;
		this.date = new Date();
		this.status = OrderStatus.PENDING;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}
	
	public Order completed() {
		status = OrderStatus.COMPLETED;
		return this;
	}
	
	public Order cancelled() {
		status = OrderStatus.CANCELLED;
		return this;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", status=" + status
				+ ", name=" + name + "]";
	}

}
