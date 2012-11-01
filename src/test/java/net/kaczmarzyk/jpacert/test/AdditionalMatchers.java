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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class AdditionalMatchers {

	private AdditionalMatchers() {
	}
	
	public static <E extends Exception> Matcher<E> causedBy(final Class<? extends Exception> causeClass) {
		return new BaseMatcher<E>() {

			@Override
			public boolean matches(Object item) {
				if (item instanceof Exception) {
					Throwable e = (Exception) item;
					while (e.getCause() != null) {
						e = e.getCause();
						if (e.getClass() == causeClass) {
							return true;
						}
					}
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("an exception with " + causeClass + " in cause chain");
			}
		};
	}
}
