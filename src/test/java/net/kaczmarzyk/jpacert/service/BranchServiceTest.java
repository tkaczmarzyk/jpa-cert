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

import static net.kaczmarzyk.jpacert.domain.AddressUtil.testAddress;
import static net.kaczmarzyk.jpacert.domain.BranchBuilder.aBranch;
import static net.kaczmarzyk.jpacert.domain.CompanyBuilder.aCompany;
import static net.kaczmarzyk.jpacert.domain.EmployeeBuilder.anEmployee;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import net.kaczmarzyk.jpacert.domain.Company;
import net.kaczmarzyk.jpacert.dto.SalaryStats;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;


public class BranchServiceTest extends EjbContainerTestBase {

	private BranchService bean;
	
	private Company company;
	
	
	@Before
	public void init() {
		bean = lookup(BranchService.class);
		
		company = aCompany("WinterSource")
				.with(aBranch(testAddress("b1"))
						.with(anEmployee("Emp1", crud)
								.withSalary(1000))
						.with(anEmployee("emp2", crud)
								.withSalary(2000))
						.with(anEmployee("Emp3", crud)
								.withSalary(6000)))
				.with(aBranch(testAddress("b2"))
						.with(anEmployee("Emp6", crud)
								.withSalary(1000))
						.with(anEmployee("Emp7", crud)
								.withSalary(2000)))
				.with(aBranch(testAddress("b3"))
						.with(anEmployee("Emp8", crud)
								.withSalary(1000)))
				.build(crud);
	}
	
	@Test
	public void shouldFilterByNumEmployees() {
		assertThat(bean.getSalaryStatsByBranch(company.getId(), 3), hasSize(1));
		assertThat(bean.getSalaryStatsByBranch(company.getId(), 2), hasSize(2));
		assertThat(bean.getSalaryStatsByBranch(company.getId(), 1), hasSize(3));
	}
	
	@Test
	public void shouldAggregateSalaries() {
		SalaryStats summary = bean.getSalaryStatsByBranch(company.getId(), 3).get(0);
		
		assertThat(summary.getAvgSalary().intValue(), is(3000));
		assertThat(summary.getMaxSalary().intValue(), is(6000));
		assertThat(summary.getMinSalary().intValue(), is(1000));
	}
}
