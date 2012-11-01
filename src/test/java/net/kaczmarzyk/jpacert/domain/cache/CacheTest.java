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
package net.kaczmarzyk.jpacert.domain.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.kaczmarzyk.jpacert.service.cache.CacheService;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CacheTest extends EjbContainerTestBase {

	private CacheService service;
	private Item item;
	
	@Before
	public void init() {
		this.service = lookup(CacheService.class);
	}
	
	@After
	public void cleanUp() {
		crud.remove(Item.class, item.getId());
	}
	
	@Test
	public void shouldAddNewEntityToCache() {
		item = crud.persistInNewTx(new Item());
		
		assertTrue(service.containsItem(item.getId()));
	}
	
	@Test
	public void shouldEvictSpecifiedEntity() {
		item = crud.persistInNewTx(new Item());
		service.evictItem(item.getId());
		
		assertFalse(service.containsItem(item.getId()));
	}
}
