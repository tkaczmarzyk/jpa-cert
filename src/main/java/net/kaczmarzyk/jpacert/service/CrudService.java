package net.kaczmarzyk.jpacert.service;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@LocalBean
public class CrudService {

	@PersistenceContext
	private EntityManager em;
	
	
	public <T> T findById(Class<T> clazz, Serializable id) {
		return em.find(clazz, id);
	}
	
	public void persist(Object entity) {
		em.persist(entity);
	}
	
	public void flushAndRefresh(Object entity) {
		em.flush();
		em.refresh(entity);
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}
}
