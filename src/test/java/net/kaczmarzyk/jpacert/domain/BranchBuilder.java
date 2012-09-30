package net.kaczmarzyk.jpacert.domain;

public class BranchBuilder {

	private Branch branch;
	
	
	private BranchBuilder(Address address) {
		this.branch = new Branch(address);
	}
	
	public BranchBuilder with(Employee emp) {
		branch.addEmployee(emp);
		return this;
	}
	
	public BranchBuilder with(EmployeeBuilder emp) {
		return with(emp.build());
	}
	
	public Branch build() {
		return branch;
	}
	
	public static BranchBuilder aBranch(Address address) {
		return new BranchBuilder(address);
	}
}
