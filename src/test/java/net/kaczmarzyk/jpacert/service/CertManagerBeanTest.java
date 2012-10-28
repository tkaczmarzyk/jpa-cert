package net.kaczmarzyk.jpacert.service;

import static org.junit.Assert.assertNotNull;
import net.kaczmarzyk.jpacert.domain.CertReceipt;
import net.kaczmarzyk.jpacert.domain.Certificate;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;

public class CertManagerBeanTest extends EjbContainerTestBase {

	private CertManagerBean bean;
	
	@Before
	public void init() {
		bean = lookup(CertManagerBean.class);
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
