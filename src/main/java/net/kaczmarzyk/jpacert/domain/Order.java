package net.kaczmarzyk.jpacert.domain;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="orders")
public class Order {

	@EmbeddedId
	private OrderKey key;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	private Customer customer;
	
	private String name;
	
	
	Order() {
	}
	
	public Order(String name, Date date) {
		this(name);
		this.key = new OrderKey(date);
	}
	
	public Order(String name) {
		this.name = name;
		this.key = new OrderKey(new Date());
		this.status = OrderStatus.PENDING;
	}
	
	public Date getDate() {
		return key.getDate();
	}

	public void setDate(Date date) {
		this.key.setDate(date);
	}

	public Long getId() {
		return key.getId();
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
	
	public OrderKey getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "Order [id=" + key.getId() + ", date=" + key.getDate() + ", status=" + status
				+ ", name=" + name + "]";
	}

}
