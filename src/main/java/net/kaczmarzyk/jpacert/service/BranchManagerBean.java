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
