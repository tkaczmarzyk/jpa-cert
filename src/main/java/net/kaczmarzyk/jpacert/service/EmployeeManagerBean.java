package net.kaczmarzyk.jpacert.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Employee;


@Stateless
@LocalBean
public class EmployeeManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public List<Employee> findByLastname(String lastname) {
		return em.createQuery("select e from Employee e where e.lastname = :lastname", Employee.class)
				.setParameter("lastname", lastname)
				.getResultList();
	}
}
