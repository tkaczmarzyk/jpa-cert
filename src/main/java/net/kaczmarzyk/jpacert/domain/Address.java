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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


@Embeddable @Access(AccessType.FIELD)
public class Address {

	private String street;
	private String city;
	private String state;
	private String zip;
	
	Address() {
	}
	
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	
	public static class AddressBuilder {
		private Address address = new Address();
		
		public AddressBuilder street(String street) {
			address.street = street;
			return this;
		}
		
		public AddressBuilder city(String city) {
			address.city = city;
			return this;
		}
		
		public AddressBuilder state(String state) {
			address.state = state;
			return this;
		}
		
		public AddressBuilder zip(String zip) {
			address.zip = zip;
			return this;
		}
		
		public Address build() {
			return address;
		}
		
		public static AddressBuilder address() {
			return new AddressBuilder();
		}
	}
}
