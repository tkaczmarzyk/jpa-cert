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
import javax.persistence.OneToMany;


@Entity
public class Company {

	@Id @GeneratedValue
	private Long id;
	
	private String name;

	@OneToMany(mappedBy="company")
	private Collection<Branch> branches;
	
	@ElementCollection
	@CollectionTable(name="company_shares")
	@MapKeyJoinColumn(name="shareholder_id")
	@Column(name="shares")
	private Map<Shareholder, Integer> shares;
	
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
}
