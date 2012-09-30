package net.kaczmarzyk.jpacert.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Employee;
import net.kaczmarzyk.jpacert.domain.PhoneType;


@Stateless
@LocalBean
public class EmployeeManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public List<Object[]> findMaxNumCertificatesByBranch() {
		return em.createQuery("select distinct b, count(c) from Branch b inner join b.employees e join e.certificates c" +
				" group by b, e" +
				" having count(c) >= ALL" +
				" (select count(e2.certificates) from Employee e2" +
				"   where e2 member of b.employees" +
				"	group by e2)" +
				" order by b.id", Object[].class)
				.getResultList();
	}
	
	public List<Employee> findByCertification(String certName) {
		return em.createQuery("select e from Employee e" +
				" where" +
				" exists (select c from e.certificates c where c.name = :cert)", Employee.class)
				.setParameter("cert", certName)
				.getResultList();
	}
	
	public String findPhoneNumber(Long employeeId, PhoneType phoneType) {
		return em.createQuery("select value(phones)" +
				" from Employee e inner join e.phoneNumbers phones" +
				" where key(phones) = :phoneType", String.class)
				.setParameter("phoneType", phoneType)
				.getSingleResult();
	}
	
	public List<Employee> findByLastname(String lastname) {
		return em.createQuery("select e from Employee e where e.lastname = :lastname", Employee.class)
				.setParameter("lastname", lastname)
				.getResultList();
	}
}
