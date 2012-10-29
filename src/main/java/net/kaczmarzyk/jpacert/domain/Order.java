package net.kaczmarzyk.jpacert.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;


@Entity
@Table(name="orders")
@SecondaryTables(
	@SecondaryTable(name="order_details", pkJoinColumns={
		@PrimaryKeyJoinColumn(name="order_id", referencedColumnName="id"),
		@PrimaryKeyJoinColumn(name="order_date", referencedColumnName="date")
	})
)
public class Order {

	@EmbeddedId
	private OrderKey key;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	private Customer customer;
	
	@OneToMany(orphanRemoval = true)
	private Collection<OrderHistory> history;
	
	private String name;
	
	@Column(table="order_details")
	private String description;
	
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
	
	public Collection<OrderHistory> getHistory() {
		return history;
	}

	public void addHistory(OrderHistory orderHistory) {
		if (history == null) {
			history = new ArrayList<>();
		}
		history.add(orderHistory);
	}
	
	@Override
	public String toString() {
		return "Order [id=" + key.getId() + ", date=" + key.getDate() + ", status=" + status
				+ ", name=" + name + "]";
	}

}
