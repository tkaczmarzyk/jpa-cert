package net.kaczmarzyk.jpacert.service;

import java.io.Serializable;
import java.util.List;

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
	
	public <T> T merge(T entity) {
		return em.merge(entity);
	}
	
	public void flushAndRefresh(Object entity) {
		em.flush();
		em.refresh(entity);
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}

	public <T> List<T> findAll(Class<T> clazz) {
		return em.createQuery("select e from " + clazz.getSimpleName() + " e", clazz)
				.getResultList();
	}
}
