package net.kaczmarzyk.jpacert.test;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;


public abstract class EjbContainerTestBase {

	private static final Object APPLICATION_NAME = "JpaCertApp";
	private static final String MODULE_NAME = "EjbModule";
	
	protected Context context;
	private EJBContainer container;
	
	@Before
	public void createContainer () {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.APP_NAME, APPLICATION_NAME);
		
		container = EJBContainer.createEJBContainer(properties);
		context = container.getContext();
	}
	
	@After
	public void closeContainer() {
		container.close();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T lookup(Class<T> beanClass) throws NamingException {
		return (T) context.lookup("java:global/" + APPLICATION_NAME + "/" + MODULE_NAME + "/" + beanClass.getSimpleName());
	}
}
