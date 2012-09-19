package net.kaczmarzyk.jpacert.customer;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.customer.domain.Customer;



@Stateless
@LocalBean
public class CustomerManagerBean {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void saveCustomer(String firstname, String lastname) {
		em.persist(new Customer(firstname, lastname));
	}
	
	public List<Customer> findCustomers(String lastname) {
		return em.createQuery("select c from Customer c where c.lastname = :lastname", Customer.class)
			.setParameter("lastname", lastname)
			.getResultList();
	}
}
