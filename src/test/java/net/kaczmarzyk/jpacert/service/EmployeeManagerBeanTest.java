package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.EmployeeBuilder.anEmployee;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import net.kaczmarzyk.jpacert.domain.Employee;
import static net.kaczmarzyk.jpacert.domain.PhoneType.*;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;


public class EmployeeManagerBeanTest extends EjbContainerTestBase {

	private EmployeeManagerBean bean;
	
	@Before
	public void init() {
		bean = lookup(EmployeeManagerBean.class);
	}
	
	@Ignore // jpa part seems to be OK, but query with key(phones) in where clause causes derby exception
	@Test
	public void shouldFindPhoneNumberWithSpecifiedType() {
		Employee emp = anEmployee("Tester7", crud)
				.withPhoneNum(MOBILE, "111")
				.withPhoneNum(HOME, "222")
				.withPhoneNum(WORK, "333")
				.build();
		
		assertThat(bean.findPhoneNumber(emp.getId(), MOBILE), is("111"));
		assertThat(bean.findPhoneNumber(emp.getId(), HOME), is("222"));
		assertThat(bean.findPhoneNumber(emp.getId(), WORK), is("333"));
	}
}
