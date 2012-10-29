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

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
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
	
	@Test
	public void findOrderWithHistoryLength_sqlShouldUseMappingToProperlyInitializeResult() {
		bean.createHistory(order);
		bean.createHistory(order);
		crud.flushAndClear();
		
		Object[] result = bean.findOrderWithHistoryLength(order.getKey());
		
		assertEquals(2, result.length);
//		assertFalse(BeanDiff.diff(order, result[0]).hasDifference()); // @OneToMany relationship is not initialized in native queries
		assertEquals(2, result[1]);
	}
}
