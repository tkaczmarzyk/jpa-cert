package net.kaczmarzyk.jpacert.example;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.naming.NamingException;

import net.kaczmarzyk.jpacert.test.EjbContainerTestBase;

import org.junit.Before;
import org.junit.Test;


public class HelloBeanTest extends EjbContainerTestBase {

	private HelloBean bean;
	
	@Before
	public void lookup() throws NamingException {
		bean = lookup(HelloBean.class);
	}
	
	@Test
	public void shouldReturnGreetings() {
		assertThat(bean.hello(), is("Hello, World!"));
	}
}
