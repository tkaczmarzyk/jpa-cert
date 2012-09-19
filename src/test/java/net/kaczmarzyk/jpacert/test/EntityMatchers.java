package net.kaczmarzyk.jpacert.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;


public abstract class EntityMatchers {

	private EntityMatchers() {
	}
	
	public static <T> Matcher<T> entityWithId(final Long id) {
		return new BaseMatcher<T>() {

			@Override
			public boolean matches(Object item) {
				try {
					Long itemId = ReflectionUtil.get(item, "id");
					return itemId.equals(id);
				} catch (Exception e) {
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("an entity with id=" + id);
			}
		};
	}
}
