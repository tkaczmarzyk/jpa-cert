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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;


/**
 * <p>A set of utility methods which provide simplified way of using reflection API.</p>
 * 
 * <p>Intended to be used <strong>only</strong> in test code</p>
 */
public abstract class ReflectionUtil {

	private ReflectionUtil() {
	}
	
	public static void set(Object target, String fieldname, Object value) {
		try {
			Class<?> classToBeUsed = target.getClass();
			do {
				try {
					Field f = classToBeUsed.getDeclaredField(fieldname);
					f.setAccessible(true);
					f.set(target, value);
					return;
				} catch (NoSuchFieldException err) {
					classToBeUsed = classToBeUsed.getSuperclass();
					if (classToBeUsed == Object.class) {
						throw err;
					}
				}
			} while (classToBeUsed != Object.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Object target, String fieldname) {
		try {
			Class<?> classToBeUsed = target.getClass();
			do {
				try {
					Field f = classToBeUsed.getDeclaredField(fieldname);
					f.setAccessible(true);
					return (T) f.get(target);
				} catch (NoSuchFieldException err) {
					classToBeUsed = classToBeUsed.getSuperclass();
					if (classToBeUsed == Object.class) {
						throw err;
					}
				}
			} while (classToBeUsed != Object.class);
			throw new NoSuchFieldException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T extends Object> T construct(Class<T> clazz){
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return  constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}
