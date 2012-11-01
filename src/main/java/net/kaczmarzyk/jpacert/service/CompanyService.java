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
import net.kaczmarzyk.jpacert.domain.Project;
import net.kaczmarzyk.jpacert.domain.Shareholder;


@Stateless
@LocalBean
public class CompanyService {

	private static final Logger log = Logger.getLogger(CompanyService.class.getName()); 
	
	@PersistenceContext
	private EntityManager em;
	
	
	@PostConstruct
	protected void init() {
		log.setLevel(Level.ALL);
	}
	
	
	public List<Project> findProjects(Long companyId, Class<? extends Project> type) {
		return em.createQuery("select p from Project p" +
				" where p.company.id = :compId" +
				" and type(p) = :pType", Project.class)
				.setParameter("compId", companyId)
				.setParameter("pType", type)
				.getResultList();
	}
	
	public Company findByName(String name) {
		return em.createNamedQuery("findCompanyByName", Company.class)
				.setParameter("name", name)
				.getSingleResult();
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

	public List<String> findProjectNames(Long companyId) {
		return em.createQuery("select case when p.name is not null then p.name" +
				" else '[noname]'" + //or just: select coalesce(p.name, '[noname]') from...
				" end" +
				" from Project p" + 
				" where p.company.id = :companyId", String.class)
				.setParameter("companyId", companyId)
				.getResultList();
	}
}
