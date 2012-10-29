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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.kaczmarzyk.jpacert.domain.CallbackEntity;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Test;


public class CallbackEntityTest extends EjbContainerTestBase {

	@Test
	public void persistCallbacksTest() {
		CallbackEntity e = new CallbackEntity();
		crud.persist(e);
		crud.flushAndClear();
		assertTrue(e.isPostPersist());
		
		e = crud.findById(CallbackEntity.class, e.getId());
		crud.flushAndClear();
		assertEquals(1, e.getPostLoadCount());
		assertEquals(1, e.getPrePersistCount());
		assertFalse(e.isPostPersist());
		
		e = crud.findById(CallbackEntity.class, e.getId());
		crud.flushAndClear();
		assertEquals(2, e.getPostLoadCount());
		assertEquals(1, e.getPrePersistCount());
		assertFalse(e.isPostPersist());
		
		e = crud.findById(CallbackEntity.class, e.getId());
		crud.persist(e);
		crud.flushAndClear();
		assertEquals(3, e.getPostLoadCount());
		assertEquals(1, e.getPrePersistCount()); // calling persist on managed entity doesn't touch callbacks
		assertFalse(e.isPostPersist());
	}
}
