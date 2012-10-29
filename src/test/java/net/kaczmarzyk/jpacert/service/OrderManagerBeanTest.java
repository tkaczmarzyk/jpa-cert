package net.kaczmarzyk.jpacert.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import net.kaczmarzyk.jpacert.domain.Order;
import net.kaczmarzyk.jpacert.domain.OrderHistory;
import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.beandiff.BeanDiff;
import org.hamcrest.Matcher;
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
	public void findOrder_sqlQueryShouldInitializeTheEntity() {
		Order o = bean.findOrder_sql(order.getKey());
		
		assertFalse(BeanDiff.diff(order, o).hasDifference());
	}
	
	@Test
	public void findOrder_shouldFindByCompositePrimaryKey() {
		assertNotNull(bean.findOrder(order.getKey()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void orphanedOrderHistoryShouldBeRemoved() {
		bean.createHistory(order);
		crud.flushAndClear();
		assertThat(crud.findAll(OrderHistory.class), (Matcher) not(empty()));
		bean.clearHistory(order);
		
		assertThat(crud.findAll(OrderHistory.class), (Matcher) empty());
	}
}
