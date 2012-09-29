package net.kaczmarzyk.jpacert.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Company;
import net.kaczmarzyk.jpacert.domain.Employee;
import net.kaczmarzyk.jpacert.domain.Shareholder;


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
	
	public List<Company> findByEmployee(Employee employee) {
		log.info("find by employee: " + employee);
		
		return em.createQuery("select distinct c from Company c inner join c.branches b" +
				" where :employee member of b.employees", Company.class)
				.setParameter("employee", employee)
				.getResultList();
	}

	public Shareholder findShareholder(String name) {
		return em.createQuery("select s from Shareholder s where s.name = :name", Shareholder.class)
				.setParameter("name", name)
				.getSingleResult();
	}
}
