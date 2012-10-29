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
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FieldResult;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;


@Entity
@Table(name="orders")
@SecondaryTables(
	@SecondaryTable(name="order_details", pkJoinColumns={
		@PrimaryKeyJoinColumn(name="order_id", referencedColumnName="id"),
		@PrimaryKeyJoinColumn(name="order_date", referencedColumnName="date")
	})
)
@SqlResultSetMappings(
	@SqlResultSetMapping(name="orderWithHistoryLengthMapping",
		entities=@EntityResult(entityClass=Order.class,
			fields={@FieldResult(name="key.id", column="order_id"),
				@FieldResult(name="key.date", column="date")
				// rest is defaulted to name=column
			}),
		columns=@ColumnResult(name="history_length"))
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
