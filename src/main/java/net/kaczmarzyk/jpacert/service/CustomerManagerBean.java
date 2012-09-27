package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.Address.AddressBuilder.address;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Customer;



@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
	
	public void saveCustomer(Customer customer) {
		em.persist(customer);
	}
	
	public List<Customer> findCustomersByLastname(String lastname) {
		return em.createQuery("select c from Customer c where c.lastname = :lastname", Customer.class)
			.setParameter("lastname", lastname)
			.getResultList();
	}
	
}
