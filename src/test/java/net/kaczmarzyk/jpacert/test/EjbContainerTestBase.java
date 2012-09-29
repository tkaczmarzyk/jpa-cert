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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class EjbContainerTestBase {

	private static final Object APPLICATION_NAME = "JpaCertApp";
	private static final String MODULE_NAME = "EjbModule";

	protected static Context context;
	private static EJBContainer container;

	@BeforeClass
	public static void createContainer() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.APP_NAME, APPLICATION_NAME);

		container = EJBContainer.createEJBContainer(properties);

		context = container.getContext();
	}

	@AfterClass
	public static void closeContainer() {
		container.close();
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
	protected <T> T lookup(Class<T> beanClass) {
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
