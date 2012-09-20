package net.kaczmarzyk.jpacert.customer.domain;

import javax.persistence.Embeddable;


@Embeddable
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
