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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries(
		@NamedQuery(name="findCompanyByName", query="select c from Company c where c.name = :name")
)
public class Company {

	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;

	@OneToMany(mappedBy="company")
	private Collection<Branch> branches;
	
	@ElementCollection
	@CollectionTable(name="company_shares")
	@MapKeyJoinColumn(name="shareholder_id")
	@Column(name="shares")
	private Map<Shareholder, Integer> shares;
	
	@OneToMany(mappedBy="company")
	private Collection<Project> projects;
	
	
	Company() {
	}
	
	public Company(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
	public Collection<Branch> getBranches() {
		if (branches == null) {
			branches = new ArrayList<>();
		}
		return branches;
	}
	
	public void addBranch(Branch branch) {
		getBranches().add(branch);
		branch.setCompany(this);
	}
	
	public Map<Shareholder, Integer> getShares() {
		if (shares == null) {
			shares = new HashMap<>();
		}
		return shares;
	}
	
	public Collection<Project> getProjects() {
		if (projects == null) {
			projects = new ArrayList<>();
		}
		return projects;
	}

	public void addProject(Project project) {
		getProjects().add(project);
		project.setCompany(this);
	}
}
