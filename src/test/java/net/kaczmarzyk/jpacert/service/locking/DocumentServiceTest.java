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
package net.kaczmarzyk.jpacert.service.locking;

import static net.kaczmarzyk.jpacert.test.AdditionalMatchers.causedBy;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.OptimisticLockException;

import net.kaczmarzyk.jpacert.domain.locking.Document;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class DocumentServiceTest extends EjbContainerTestBase {

	private static DocumentService service;

	@Before
	public void init() {
		service = lookup(DocumentService.class);

		disableLogs(); // some exceptions are expected in this test
	}

	@After
	public void enableLogs() {
		restoreLogs();
	}

	@AfterClass
	public static void cleanUp() {
		// documentService does everything in new transaction, so changes won't be rolled back
		// we need to clean database explicitly
		service.deleteAll();
		System.out.println("test");
	}

	@Test
	public void shouldNotAllowConcurrentModification() {
		Document doc = service.newDoc("hello"); // in new tx

		doc = crud.findById(Document.class, doc.getId()); // becomes managed for the whole test method

		service.append(doc.getId(), " world!"); // in new tx

		try {
			doc.append(" abc");
			crud.flushAndClear();
			fail("optimistic lock exception expected");
		} catch (EJBTransactionRolledbackException ex) {
			assertThat(ex, causedBy(OptimisticLockException.class));
		}
	}
}
