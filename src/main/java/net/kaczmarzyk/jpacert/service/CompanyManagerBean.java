package net.kaczmarzyk.jpacert.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Address;
import net.kaczmarzyk.jpacert.domain.Company;
import net.kaczmarzyk.jpacert.domain.Employee;


@Stateless
@LocalBean
public class CompanyManagerBean {

	private static final Logger log = Logger.getLogger(CompanyManagerBean.class.getName()); 
	
	@PersistenceContext
	private EntityManager em;
	
	
	@PostConstruct
	protected void init() {
		log.setLevel(Level.ALL);
	}
	
	public void save(Company company) {
		em.persist(company);
	}
	
	public Company findByAddress(Address address) { //FIXME both fail for some reason :(
//		return em.createQuery("select comp from Company comp where :address member of comp.addresses", Company.class)
//			.setParameter("address", address)
//			.getResultList();
		
		return em.createQuery("select distinct c from Company c join c.addresses a where a in (:address)", Company.class)
				.setParameter("address", Arrays.asList(address))
				.getSingleResult();
	}
	
	public List<Company> findByEmployee(Employee employee) {
		log.info("find by employee: " + employee);
		
		return em.createQuery("select c from Company c where :employee member of c.employees", Company.class)
				.setParameter("employee", employee)
				.getResultList();
	}
}
