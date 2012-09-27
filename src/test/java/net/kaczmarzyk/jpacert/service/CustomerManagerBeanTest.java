package net.kaczmarzyk.jpacert.service;

import static net.kaczmarzyk.jpacert.domain.AddressUtil.testAddress;
import static net.kaczmarzyk.jpacert.domain.CustomerMatchers.customer;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;
import static net.kaczmarzyk.jpacert.test.DateUtil.newDate;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.jpacert.domain.Customer;
import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;


public class CustomerManagerBeanTest extends EjbContainerTestBase {

	private CustomerManagerBean bean;
	
	@Before
	public void lookup() {
		bean = lookup(CustomerManagerBean.class);
	}
	
	@Test
	public void shouldBePossibleToFindPersistedCustomer() {
		bean.createCustomer("Tom", "McExample");
		
		assertThat(bean.findCustomersByLastname("McExample"), hasItem(customer("Tom", "McExample")));
	}

	/*
	 * Interesting thing is, that if you enable shared-cache (see persistence.xml),
	 * then getById will return cached entity without order specified in the mapping
	 * With cache mode set to NONE we're sure we actually hit database and that
	 * "order by" is performed as expected in the test. 
	 */
	@Test
	public void ordersShouldBeOrderedByDate() {
		Customer customer = new Customer("Tester", "McTest", testAddress());
		customer.addOrder(new Order("testOrder1", newDate(2012, 9, 3)));
		customer.addOrder(new Order("testOrder2", newDate(2012, 10, 2)));
		customer.addOrder(new Order("testOrder3", newDate(2012, 9, 1)));
		
		bean.saveCustomer(customer);
		
		Customer customer2 = bean.getById(customer.getId());
		assertEquals(newDate(2012, 10, 2), customer2.getOrders().get(0).getDate());
		assertEquals(newDate(2012, 9, 3), customer2.getOrders().get(1).getDate());
		assertEquals(newDate(2012, 9, 1), customer2.getOrders().get(2).getDate());
	}
	
}
