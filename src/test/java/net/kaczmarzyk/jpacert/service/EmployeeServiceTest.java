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
import static net.kaczmarzyk.jpacert.domain.EmployeeBuilder.anEmployee;
import static net.kaczmarzyk.jpacert.domain.PhoneType.HOME;
import static net.kaczmarzyk.jpacert.domain.PhoneType.MOBILE;
import static net.kaczmarzyk.jpacert.domain.PhoneType.WORK;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import net.kaczmarzyk.jpacert.domain.Branch;
import net.kaczmarzyk.jpacert.domain.Employee;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class EmployeeServiceTest extends EjbContainerTestBase {

	private EmployeeService bean;
	
	@Before
	public void init() {
		bean = lookup(EmployeeService.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldFindMaxNumOfCertsInBranch() {
		Branch b1 = aBranch(testAddress("b1"))
				.with(anEmployee("Master1", crud)
						.withCertificates("Java", "JPA", "EJB"))
				.with(anEmployee("Junior1", crud)
						.withCertificates("Java"))
				.build(crud);
		Branch b2 = aBranch(testAddress("b2"))
				.with(anEmployee("Pro1", crud)
						.withCertificates("Java", "Web"))
				.with(anEmployee("Pro2", crud)
						.withCertificates("Java", "Ejb"))
				.build(crud);
		
		List<Object[]> result = bean.findMaxNumCertificatesByBranch();
		
		assertThat(result, hasSize(2));
		assertThat(result, hasItems(entry(b1, 3), entry(b2, 2)));
		
		List<Object[]> result2 = bean.findMaxNumCertificatesByBranch_criteria();
		
		assertThat(result2, hasSize(2));
		assertThat(result2, hasItems(entry(b1, 3), entry(b2, 2)));
		
		List<Object[]> result3 = bean.findMaxNumCertificatesByBranch_criteriaTypeSafe();
		
		assertThat(result3, hasSize(2));
		assertThat(result3, hasItems(entry(b1, 3), entry(b2, 2)));
	}
	
	private Matcher<Object[]> entry(final Branch branch, final long numCerts) {
		return new BaseMatcher<Object[]>() {
			public boolean matches(Object item) {
				if (item instanceof Object[]) {
					Object[] array = (Object[]) item;
					return array.length == 2 && ((Branch)array[0]).getId().equals(branch.getId()) && array[1].equals(numCerts);
				}
				return false;
			}
			public void describeTo(Description description) {
			}
		};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) // FIXME solve hamcrest problem
	@Test
	public void shouldFindEmployeeWithTheCertificate() {
		Employee e1 = anEmployee("Certified1", crud)
				.withCertificate("Jpa Professional")
				.withCertificate("Ejb Professional").build();
		Employee e2 = anEmployee("Certified2", crud)
				.withCertificate("Jpa Professional").build();
		
		assertThat(bean.findByCertification("Jpa Professional"), allOf((Matcher) hasSize(2), (Matcher) hasItems(e1, e2)));
		assertThat(bean.findByCertification("Ejb Professional"), allOf((Matcher) hasSize(1), (Matcher) hasItems(e1)));
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
