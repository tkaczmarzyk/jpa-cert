package net.kaczmarzyk.jpacert.domain;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;


public abstract class CompanyMatchers {

	private CompanyMatchers() {
	}
	
	public static Matcher<Company> company(final String name) {
		return new BaseMatcher<Company>() {

			@Override
			public boolean matches(Object item) {
				if (item instanceof Company) {
					return name.equals(((Company)item).getName());
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("a company with name '" + name + "'");
			}
		};
	}
}
