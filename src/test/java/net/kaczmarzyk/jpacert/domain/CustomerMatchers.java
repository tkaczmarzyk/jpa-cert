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
