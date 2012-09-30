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
