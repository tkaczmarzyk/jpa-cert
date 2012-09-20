package net.kaczmarzyk.jpacert.customer;

import static net.kaczmarzyk.jpacert.customer.CustomerMatchers.customer;
import static net.kaczmarzyk.jpacert.test.AssertUtil.assertThat;
import static net.kaczmarzyk.jpacert.test.EntityMatchers.entityWithId;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;

import java.util.List;

import net.kaczmarzyk.jpacert.domain.Address;
import net.kaczmarzyk.jpacert.domain.Customer;
import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.service.CustomerManagerBean;
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

	@Test
	public void findCustomerWithPendingOrders_shouldReturnAllCustomersWithAtLeastOnePendingOrder() {
		Customer customerWithoutAnyOrder = new Customer("Tester", "McTest", testAddress());
		bean.saveCustomer(customerWithoutAnyOrder);
		
		Customer customerWithPendingOrder = new Customer("Tester II", "McTest", testAddress());
		customerWithPendingOrder.addOrder(new Order("testOrder1").completed());
		customerWithPendingOrder.addOrder(new Order("testOrder2"));
		bean.saveCustomer(customerWithPendingOrder);
		
		Customer customerWithoutPendingOrder = new Customer("Tester III", "McTest", testAddress());
		customerWithoutPendingOrder.addOrder(new Order("testOrder4").cancelled());
		customerWithoutPendingOrder.addOrder(new Order("testOrder5").completed());
		bean.saveCustomer(customerWithoutPendingOrder);
		
		List<Customer> customersFound = bean.findCustomerWithPendingOrders();
		assertEquals(1, customersFound.size());
		assertThat(customersFound, hasItem(entityWithId(customerWithPendingOrder.getId())));
	}

	private Address testAddress() {
		return new Address.AddressBuilder().street("street").city("city").zip("zip").state("state").build();
	}
}
