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

import static org.junit.Assert.assertNotNull;
import net.kaczmarzyk.jpacert.domain.CertReceipt;
import net.kaczmarzyk.jpacert.domain.Certificate;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;

public class CertServiceTest extends EjbContainerTestBase {

	private CertService bean;
	
	@Before
	public void init() {
		bean = lookup(CertService.class);
	}
	
	@Test
	public void createReceipt_mappedIdShouldBeInitialized() {
		Certificate cert = new Certificate("enterprise architect");
		crud.persist(cert);
		crud.flushAndClear();
		
		CertReceipt receipt = bean.createReceipt(cert);
		assertNotNull(receipt.getCertId());
	}
}
