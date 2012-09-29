package net.kaczmarzyk.jpacert.domain;

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
	
	public static CompanyBuilder aCompany(String name) {
		return new CompanyBuilder(name);
	}
	
}
