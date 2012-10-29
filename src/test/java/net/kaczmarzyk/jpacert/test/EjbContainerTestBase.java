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
package net.kaczmarzyk.jpacert.test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import net.kaczmarzyk.jpacert.service.CrudService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class EjbContainerTestBase {

	private static final Object APPLICATION_NAME = "JpaCertApp";
	private static final String MODULE_NAME = "EjbModule";

	protected static Context context;
	protected static CrudService crud;
	private static EJBContainer container;

	@BeforeClass
	public static void createContainer() {
		if (container == null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(EJBContainer.APP_NAME, APPLICATION_NAME);
	
			container = EJBContainer.createEJBContainer(properties);
			context = container.getContext();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						container.close();
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			});
			
			crud = lookup(CrudService.class);
		}
	}

	@AfterClass
	public static void closeContainer() {
		//container.close();
	}

	@Before
	public void begin() throws Exception {
		getTransactionManager().begin();
	}

	@After
	public void rollback() throws Exception {
		getTransactionManager().rollback();
	}

	@SuppressWarnings("unchecked")
	protected static <T> T lookup(Class<T> beanClass) {
		try {
			return (T) context.lookup("java:global/" + APPLICATION_NAME + "/" + MODULE_NAME + "/" + beanClass.getSimpleName());
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}

	protected DataSource getDataSource() {
		try {
			return (DataSource) context.lookup("jdbc/__default");
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected TransactionManager getTransactionManager() throws NamingException, Exception {
		return (TransactionManager) context.lookup("java:appserver/TransactionManager");
	}

	protected void enableTransactionLogs() {
		Logger logger = Logger.getLogger("javax.enterprise.resource.jta");
		logger.addHandler(new ConsoleHandler());

		setLevel(Level.ALL, logger);
	}

	private void setLevel(Level level, Logger logger) {
		logger.setLevel(level);

		for (Handler handler : logger.getHandlers()) {
			handler.setLevel(level);
		}
	}
}
