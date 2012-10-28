package net.kaczmarzyk.jpacert.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;

public class OrderManagerBeanTest extends EjbContainerTestBase {

	private OrderManagerBean bean;
	private Order order;
	
	@Before
	public void init() {
		bean = lookup(OrderManagerBean.class);
		
		order = new Order("programming in scala", new Date());
		crud.persist(order);
		crud.flushAndClear();
	}
	
	@Test
	public void findOrder_shouldFindByCompositePrimaryKey() {
		assertNotNull(bean.findOrder(order.getKey()));
	}
	
}
