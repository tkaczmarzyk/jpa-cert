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


public class BranchManagerBeanTest extends EjbContainerTestBase {

	private BranchManagerBean bean;
	
	private Company company;
	
	
	@Before
	public void init() {
		bean = lookup(BranchManagerBean.class);
		
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
