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
