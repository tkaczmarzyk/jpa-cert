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

public class CompanyBuilder {

	private Company company;
	
	
	private CompanyBuilder(String companyName) {
		this.company = new Company(companyName);
	}

	public CompanyBuilder with(BranchBuilder builder) {
		company.addBranch(builder.build());
		return this;
	}
	
	public CompanyBuilder with(ProjectBuilder builder, ProjectBuilder... builders) {
		company.addProject(builder.build());
		for (ProjectBuilder b : builders) {
			company.addProject(b.build());
		}
		return this;
	}
	
	public Company build() {
		return company;
	}
	
	public Company build(CrudService crud) {
		build();
		crud.persist(company);
		return company;
	}
	
	public static CompanyBuilder aCompany(String name) {
		return new CompanyBuilder(name);
	}
	
}
