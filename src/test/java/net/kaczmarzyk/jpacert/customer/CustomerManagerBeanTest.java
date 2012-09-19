package net.kaczmarzyk.jpacert.customer;

import static net.kaczmarzyk.jpacert.customer.CustomerMatchers.customer;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;


public class CustomerManagerBeanTest extends EjbContainerTestBase {

	private CustomerManagerBean bean;
	
	@Before
	public void lookup() {
		bean = lookup(CustomerManagerBean.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void shouldBePossibleToFindPersistedCustomer() {
		bean.saveCustomer("Tom", "McExample");
		
		assertThat(bean.findCustomers("McExample"), (Matcher) hasItem(customer("Tom", "McExample")));
	}

}
