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
