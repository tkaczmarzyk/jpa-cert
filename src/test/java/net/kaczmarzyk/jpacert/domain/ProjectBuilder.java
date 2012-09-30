package net.kaczmarzyk.jpacert.domain;

public class ProjectBuilder {

	private Project project;
	
	private ProjectBuilder(Project p) {
		this.project = p;
	}
	
	public Project build() {
		return project;
	}
	
	public static ProjectBuilder aFixedPriceProject(String name) {
		return new ProjectBuilder(new FixedPriceProject(name));
	}
	
	public static ProjectBuilder aTimeAndMaterialProject(String name) {
		return new ProjectBuilder(new TimeAndMaterialProject(name));
	}
	
	public static ProjectBuilder anInternalProject(String name) {
		return new ProjectBuilder(new InternalProject(name));
	}
}
