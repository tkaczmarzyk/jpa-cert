package net.kaczmarzyk.jpacert.service;

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

import net.kaczmarzyk.jpacert.domain.Branch;
import net.kaczmarzyk.jpacert.domain.Certificate;
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
	
	public List<Object[]> findMaxNumCertificatesByBranch_criteria() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		
		Root<Branch> branch = query.from(Branch.class);
		Join<Branch, Employee> employee = branch.join("employees");
		Join<Employee, Certificate> cert = employee.join("certificates");
		
		Subquery<Long> sq = query.subquery(Long.class);
		Root<Employee> emp2 = sq.from(Employee.class);
		sq.select(cb.count(emp2.get("certificates")))
			.where(cb.isMember(emp2, branch.<List<Employee>>get("employees")))
			.groupBy(emp2);
		
		query.multiselect(branch, cb.count(cert)).distinct(true)
			.groupBy(branch, employee)
			.having(cb.greaterThanOrEqualTo(cb.count(cert), cb.all(sq)))
			.orderBy(cb.asc(branch.get("id")));
		
		return em.createQuery(query)
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
