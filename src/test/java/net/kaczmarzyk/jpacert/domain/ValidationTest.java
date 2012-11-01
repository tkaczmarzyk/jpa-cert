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
package net.kaczmarzyk.jpacert.domain;
import static net.kaczmarzyk.jpacert.test.AdditionalMatchers.causedBy;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.ejb.EJBTransactionRolledbackException;
import javax.validation.ConstraintViolationException;

import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ValidationTest extends EjbContainerTestBase {

	
	@Before
	public void disableLogs() {
		// to avoid confusion during commandline builds
		super.disableLogs();
	}
	
	@After
	public void enableLogs() {
		restoreLogs();
	}
	
	@Test
	public void shouldThrowValidationExceptionIfSalaryIsBelowTheMinimum() {
		Employee e = new Employee("Doe", new BigDecimal("900"));
		
		try {
			crud.persist(e);
			crud.flushAndClear();
			fail("validation exception expected");
		} catch (EJBTransactionRolledbackException ex) {
			assertThat(ex, causedBy(ConstraintViolationException.class));
		}
	}
	
	@Test
	public void shouldNotAllowToLowerTheSalary() {
		Employee e = new Employee("Homer", new BigDecimal("5000"));
		crud.persist(e);
		crud.flushAndClear();
		
		try {
			e.setSalary(new BigDecimal("4100"));
			crud.merge(e);
			crud.flushAndClear();
		} catch (EJBTransactionRolledbackException ex) {
			assertThat(ex, causedBy(ConstraintViolationException.class));
		}
	}
	
	@Test
	public void shouldAllowToRaiseTheSalary() {
		Employee e = new Employee("Homer", new BigDecimal("5000"));
		crud.persist(e);
		crud.flushAndClear();
		
		e.setSalary(new BigDecimal("5100"));
		crud.merge(e);
	}
}
