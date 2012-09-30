package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.AddressUtil.testAddress;
import static net.kaczmarzyk.jpacert.domain.BranchBuilder.aBranch;
import static net.kaczmarzyk.jpacert.domain.CompanyBuilder.aCompany;
import static net.kaczmarzyk.jpacert.domain.CompanyMatchers.company;
import static net.kaczmarzyk.jpacert.domain.EmployeeBuilder.anEmployee;
import static net.kaczmarzyk.jpacert.domain.ProjectBuilder.aFixedPriceProject;
import static net.kaczmarzyk.jpacert.domain.ProjectBuilder.aTimeAndMaterialProject;
import static net.kaczmarzyk.jpacert.domain.ProjectBuilder.anInternalProject;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import net.kaczmarzyk.jpacert.domain.Company;
import net.kaczmarzyk.jpacert.domain.Employee;
import net.kaczmarzyk.jpacert.domain.InternalProject;
import net.kaczmarzyk.jpacert.domain.Shareholder;
import net.kaczmarzyk.jpacert.domain.TimeAndMaterialProject;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;


public class CompanyManagerBeanTest extends EjbContainerTestBase {

	private CompanyManagerBean companyBean;
	private EmployeeManagerBean emploeeBean;
	
	private Company comp1;
	private Company comp2;
	
	
	@Before
	public void init() {
		companyBean = lookup(CompanyManagerBean.class);
		emploeeBean = lookup(EmployeeManagerBean.class);
		
		comp1 = aCompany("Testers & CO")
			.with(aBranch(testAddress("b1"))
					.with(anEmployee("McTest", crud))
					.with(anEmployee("McTest2", crud)))
			.build(crud);
		
		comp2 = aCompany("Jpa Certified Devs")
			.with(aBranch(testAddress("b2"))
					.with(anEmployee("Eclipselinker", crud)))
			.with(aBranch(testAddress("b3"))
					.with(anEmployee("Hibernatus", crud))
					.with(anEmployee("OpenJdker", crud)))
			.with(aFixedPriceProject("Lottery Engine"))
			.with(aTimeAndMaterialProject("Integration"))
			.with(anInternalProject("RnD"))
			.with(anInternalProject("Business Processes"))
			.build(crud);
	}
	
	@Test
	public void showTables() throws SQLException {
		DataSource ds = getDataSource();
		try (Connection connection = ds.getConnection()) {
			DatabaseMetaData dbmd = connection.getMetaData();
			try (ResultSet tableSet = dbmd.getTables(null, null, null, null)) {
				while (tableSet.next()) {
				    String strTableName = tableSet.getString("TABLE_NAME");
				    if (!strTableName.startsWith("SYS")) {
				    	System.out.println("+ " + strTableName);
				    	try (ResultSet columnSet = dbmd.getColumns(null, null, strTableName, null)) {
					    	while (columnSet.next()) {
					    		System.out.println("`-- " + columnSet.getString("COLUMN_NAME"));
					    	}
				    	}
				    }
				}
			}
		}
	}
	
	@Test
	public void shouldFindCompanyWithProvidedName() {
		Company company = companyBean.findByName("Jpa Certified Devs");
		assertThat(company.getId(), is(comp2.getId()));
	}
	
	@Test
	public void shouldMaintainShares() {
		Shareholder g1 = new Shareholder("Geek1");
		crud.persist(g1);
		comp1.getShares().put(g1, 11);
		Shareholder g2 = new Shareholder("Geek2");
		crud.persist(g2);
		comp1.getShares().put(g2, 81);
		
		crud.flushAndClear();
		
		g1 = companyBean.findShareholder("Geek1");
		g2 = companyBean.findShareholder("Geek2");
		comp1 = crud.findById(Company.class, comp1.getId());
		
		assertThat(comp1.getShares().get(g1), is(11));
		assertThat(comp1.getShares().get(g2), is(81));
	}
	
	@Test
	public void findByEmployee_shouldReturnAllCompaniesWichHaveTheEmployeeInTheirCollection() {
		Employee emp = emploeeBean.findByLastname("Hibernatus").get(0);
		List<Company> companies = companyBean.findByEmployee(emp);
		
		assertThat(companies, hasSize(1));
		assertThat(companies, hasItem(company("Jpa Certified Devs")));
	}
	
	@Test
	public void shouldFilterProjectsByType() {
		assertThat(companyBean.findProjects(comp2.getId(), InternalProject.class), hasSize(2));
		assertThat(companyBean.findProjects(comp2.getId(), TimeAndMaterialProject.class), hasSize(1));
	}
}
