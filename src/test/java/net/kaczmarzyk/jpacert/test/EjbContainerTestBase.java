package net.kaczmarzyk.jpacert.test;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;


public abstract class EjbContainerTestBase {

	private static final Object APPLICATION_NAME = "JpaCertApp";
	private static final String MODULE_NAME = "EjbModule";
	
	protected static Context context;
	private static EJBContainer container;
	
	@BeforeClass
	public static void createContainer () {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.APP_NAME, APPLICATION_NAME);
		
		container = EJBContainer.createEJBContainer(properties);
		context = container.getContext();
	}
	
	@AfterClass
	public static void closeContainer() {
		container.close();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T lookup(Class<T> beanClass) {
		try {
			return (T) context.lookup("java:global/" + APPLICATION_NAME + "/" + MODULE_NAME + "/" + beanClass.getSimpleName());
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}
}
