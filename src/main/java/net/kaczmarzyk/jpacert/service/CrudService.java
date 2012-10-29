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

	public void refresh(Object entity) {
		em.refresh(entity);
	}
}
