package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.AddressUtil.testAddress;
import static net.kaczmarzyk.jpacert.domain.CompanyMatchers.company;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;

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
	public void findByEmployee_shouldReturnAllCompaniesWichHaveTheAddressInTheirCollection() {
		Company comp1 = new Company("Testers & CO", testAddress("1"));
		comp1.addEmployee(new Employee("McTest"));
		comp1.addEmployee(new Employee("McTest2"));
		companyBean.save(comp1);
		Company comp2 = new Company("Jpa Certified Devs", testAddress("2"));
		comp2.addEmployee(new Employee("Hibernatus"));
		comp2.addEmployee(new Employee("Eclipselinker"));
		companyBean.save(comp2);
		
		Employee emp = emploeeBean.findByLastname("Hibernatus").get(0);
		List<Company> companies = companyBean.findByEmployee(emp);
		
		assertThat(companies, hasSize(1));
		assertThat(companies, hasItem(company("Jpa Certified Devs")));
	}
}
