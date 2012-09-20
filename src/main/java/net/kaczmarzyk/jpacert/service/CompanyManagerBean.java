package net.kaczmarzyk.jpacert.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.Address;
import net.kaczmarzyk.jpacert.domain.Company;


@Stateless
@LocalBean
public class CompanyManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public void save(Company company) {
		em.persist(company);
	}
	
	public List<Company> findByAddress(Address address) {
		return em.createQuery("select comp from Company comp where :address in (comp.addresses)", Company.class)
			.setParameter("address", address)
			.getResultList();
	}
}
