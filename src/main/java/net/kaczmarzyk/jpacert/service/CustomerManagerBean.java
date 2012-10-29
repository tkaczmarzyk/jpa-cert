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

import static net.kaczmarzyk.jpacert.domain.Address.AddressBuilder.address;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import net.kaczmarzyk.jpacert.domain.Customer;
import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.domain.OrderStatus;



@Stateless
@LocalBean
public class CustomerManagerBean {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void createCustomer(String firstname, String lastname) {
		em.persist(new Customer(firstname, lastname, address().street("street").city("city").state("state").zip("zip").build()));
	}
	
	public Customer getById(Long id) {
		Customer c = em.find(Customer.class, id);
		
		c.getOrders().size(); // to be sure that lazy-loaded collection is loaded
							// Even though Eclipselink is able to load it after em is closed,
							// we need to initialize it manually if we want the app to be portable (JPA spec compatible)
		
		return c;
	}
	
	public List<Customer> findCustomersByLastname(String lastname) {
		return em.createQuery("select c from Customer c where c.lastname = :lastname", Customer.class)
			.setParameter("lastname", lastname)
			.getResultList();
	}
	
	public List<Customer> findCustomerWithPendingOrders() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
		
		Root<Customer> customer = query.from(Customer.class);
		
		Subquery<Order> sq = query.subquery(Order.class);
		Root<Customer> sqCustomer = sq.correlate(customer);
		Join<Customer, Order> order = sqCustomer.join("orders");
		sq.select(order);
		sq.where(cb.equal(order.get("status"), OrderStatus.PENDING));
		
		query.select(customer);
		query.where(cb.exists(sq));
		
		return em.createQuery(query).getResultList();
	}

}
