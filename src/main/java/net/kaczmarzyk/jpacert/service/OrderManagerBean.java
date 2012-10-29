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
package net.kaczmarzyk.jpacert.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.domain.OrderHistory;
import net.kaczmarzyk.jpacert.domain.OrderKey;

@Stateless
@LocalBean
public class OrderManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public Order findOrder(OrderKey key) {
//		return em.find(Order.class, key);
		
		return em.createQuery("select o from Order o where o.key = :key", Order.class)
				.setParameter("key", key)
				.getSingleResult();
	}

	public Order findOrder_sql(OrderKey key) {
		return (Order) em.createNativeQuery("select * from orders where id = ?id and date = ?date", Order.class)
				.setParameter("id", key.getId())
				.setParameter("date", key.getDate())
				.getSingleResult();
	}
	
	public Object[] findOrderWithHistoryLength(OrderKey key) {
		Object[] result = (Object[]) em.createNativeQuery("select id as order_id, date, status, customer_id, name," +
				" (select count(1) from orders_orderhistory where id = ?id and date = ?date) history_length" +
				" from orders" +
				" where id = ?id and date =?date", "orderWithHistoryLengthMapping")
				.setParameter("id", key.getId())
				.setParameter("date", key.getDate())
				.getSingleResult();
		return result;
	}

	public OrderHistory createHistory(Order order) {
		if (!em.contains(order)) {
			order = em.find(Order.class, order.getKey());
		}
		OrderHistory orderHistory = new OrderHistory();
		order.addHistory(orderHistory);
		return orderHistory;
	}


	public void clearHistory(Order order) {
		if (!em.contains(order)) {
			order = em.find(Order.class, order.getKey());
		}
		order.getHistory().clear();
	}
	
}
