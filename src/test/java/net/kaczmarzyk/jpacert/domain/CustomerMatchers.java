package net.kaczmarzyk.jpacert.domain;

import net.kaczmarzyk.jpacert.domain.Customer;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class CustomerMatchers {

	private CustomerMatchers() {
	}
	
	public static Matcher<Customer> customer(final Long id) {
		return new BaseMatcher<Customer>() {
			
			@Override
			public boolean matches(Object item) {
				if (item instanceof Customer) {
					Customer customer = (Customer) item;
					return id.equals(customer.getId());
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Customer (" + id + ")");
			}
		};
	}
	
	public static Matcher<Customer> customer(final String firstname, final String lastname) {
		return new BaseMatcher<Customer>() {
			
			@Override
			public boolean matches(Object item) {
				if (item instanceof Customer) {
					Customer customer = (Customer) item;
					return firstname.equals(customer.getFirstname()) && lastname.equals(customer.getLastname());
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Customer (" + firstname + " " + lastname + ")");
			}
		};
	}
}
