package net.kaczmarzyk.jpacert.customer;

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

import net.kaczmarzyk.jpacert.customer.domain.Customer;
import net.kaczmarzyk.jpacert.customer.domain.Order;
import net.kaczmarzyk.jpacert.customer.domain.OrderStatus;



@Stateless
@LocalBean
public class CustomerManagerBean {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void createCustomer(String firstname, String lastname) {
		em.persist(new Customer(firstname, lastname));
	}
	
	public void saveCustomer(Customer customer) {
		em.persist(customer);
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
