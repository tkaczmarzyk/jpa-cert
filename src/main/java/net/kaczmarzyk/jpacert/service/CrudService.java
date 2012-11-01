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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;


@Stateless
@LocalBean
public class CrudService {

	@PersistenceContext
	private EntityManager em;
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	
	@SuppressWarnings("unchecked")
	public <T> T findByExample(T detachedEntity) {
		PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
		return (T) findById(detachedEntity.getClass(), util.getIdentifier(detachedEntity));
	}
	
	public <T> T findById(Class<T> clazz, Object id) {
		return findById(clazz, id, new HashMap<String, Object>());
	}
	
	public <T> T findById(Class<T> clazz, Object id, Map<String, Object> props) {
		return em.find(clazz, id, props);
	}
	
	public <T> T persist(T entity) {
		em.persist(entity);
		return entity;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T> T persistInNewTx(T entity) {
		return persist(entity);
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

	public void refresh(Object entity) {
		em.refresh(entity);
	}

	public <T> T findById(Class<T> clazz, Long id, LockModeType lockMode) {
		return em.find(clazz, id, lockMode);
	}

	public void remove(Class<?> clazz, Object id) {
		Object entity = em.getReference(clazz, id);
		em.remove(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T> T findbyIdInNewTx(Class<T> clazz, Long id) {
		return findById(clazz, id);
	}
	
}
