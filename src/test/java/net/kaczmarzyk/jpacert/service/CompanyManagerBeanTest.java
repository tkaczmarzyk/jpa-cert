package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.CompanyMatchers.company;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static net.kaczmarzyk.jpacert.domain.AddressUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static net.kaczmarzyk.jpacert.domain.CompanyBuilder.*;
import static net.kaczmarzyk.jpacert.domain.BranchBuilder.*;
import java.util.List;

import net.kaczmarzyk.jpacert.domain.Company;
import net.kaczmarzyk.jpacert.domain.Employee;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;


public class CompanyManagerBeanTest extends EjbContainerTestBase {

	private CompanyManagerBean companyBean;
	private EmployeeManagerBean emploeeBean;
	
	
	@Before
	public void lookup() {
		companyBean = lookup(CompanyManagerBean.class);
		emploeeBean = lookup(EmployeeManagerBean.class);
	}
	
	@Test
	public void findByEmployee_shouldReturnAllCompaniesWichHaveTheEmployeeInTheirCollection() {
		Company comp1 = aCompany("Testers & CO")
				.with(aBranch(testAddress("b1"))
						.with(new Employee("McTest"))
						.with(new Employee("McTest2")))
				.build();
		companyBean.save(comp1);
		Company comp2 = aCompany("Jpa Certified Devs")
				.with(aBranch(testAddress("b2"))
						.with(new Employee("Eclipselinker")))
				.with(aBranch(testAddress("b3"))
						.with(new Employee("Hibernatus")))
				.build();
		companyBean.save(comp2);
		
		Employee emp = emploeeBean.findByLastname("Hibernatus").get(0);
		List<Company> companies = companyBean.findByEmployee(emp);
		
		assertThat(companies, hasSize(1));
		assertThat(companies, hasItem(company("Jpa Certified Devs")));
	}
}
