package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="project_type")
public abstract class Project {

	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;
	
	@ManyToOne
	private Company company;
	
	Project() {
	}
	
	public Project(String name) {
		this.name = name;
	}

	
	public Company getCompany() {
		return company;
	}

	void setCompany(Company company) {
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
