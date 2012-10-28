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
