package net.kaczmarzyk.jpacert.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.dto.SalaryStats;


@Stateless
@LocalBean
public class BranchManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public List<SalaryStats> getSalaryStatsByBranch(Long companyId, int minNumEmployees) {
		return em.createQuery(
				"select new net.kaczmarzyk.jpacert.dto.SalaryStats(b, max(e.salary), min(e.salary), avg(e.salary))" +
				" from Branch b inner join b.employees e" +
				" where b.company.id = :companyId" +
				" group by b" +
				" having count(e) >= :minEmps", SalaryStats.class)
				.setParameter("companyId", companyId)
				.setParameter("minEmps", minNumEmployees)
				.getResultList();
	}
}
