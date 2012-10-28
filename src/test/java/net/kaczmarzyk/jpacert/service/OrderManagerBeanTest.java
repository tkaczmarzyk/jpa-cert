package net.kaczmarzyk.jpacert.service;

import static org.junit.Assert.*;

import java.util.Date;

import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;

public class OrderManagerBeanTest extends EjbContainerTestBase {

	private OrderManagerBean bean;
	
	@Before
	public void init() {
		bean = lookup(OrderManagerBean.class);
	}
	
	@Test
	public void findOrder_shouldFindByCompositePrimaryKey() {
		Order order = new Order("programming in scala", new Date());
		crud.persist(order);
		
		crud.flushAndClear();
		
		assertNotNull(bean.findOrder(order.getKey()));
	}
}
