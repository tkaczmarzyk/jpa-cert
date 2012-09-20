package net.kaczmarzyk.jpacert.domain;

public abstract class AddressUtil {

	private AddressUtil() {
	}
	
	public static Address testAddress() {
		return testAddress("");
	}
	
	public static Address testAddress(String suffix) {
		return new Address.AddressBuilder()
				.street("street" + suffix)
				.city("city" + suffix)
				.zip("zip" + suffix)
				.state("state" + suffix)
				.build();
	}
}
