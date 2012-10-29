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

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.jpacert.domain.CertReceipt;
import net.kaczmarzyk.jpacert.domain.Certificate;

@Stateless
@LocalBean
public class CertManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	
	public CertReceipt createReceipt(Certificate cert) {
		if (!em.contains(cert)) {
			cert = em.find(Certificate.class, cert.getId());
		}
		
		CertReceipt receipt = new CertReceipt(cert);
		em.persist(receipt);
		
		return receipt;
	}
}
