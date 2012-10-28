package net.kaczmarzyk.jpacert.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Order;
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
}
