package net.kaczmarzyk.jpacert.domain;

import net.kaczmarzyk.jpacert.service.CrudService;

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
	
	public Branch build(CrudService crud) {
		build();
		crud.persist(branch);
		return branch;
	}
	
	public static BranchBuilder aBranch(Address address) {
		return new BranchBuilder(address);
	}

}
