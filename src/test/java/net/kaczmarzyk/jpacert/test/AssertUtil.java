package net.kaczmarzyk.jpacert.test;

import org.hamcrest.Matcher;
import org.junit.Assert;

public abstract class AssertUtil {

	private AssertUtil() {
	}
	
	@SuppressWarnings("unchecked")
	public static void assertThat(Object object, @SuppressWarnings("rawtypes") Matcher matcher) {
		Assert.assertThat(object, matcher);
	}
}
