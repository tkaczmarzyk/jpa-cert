package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Shareholder {

	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;
	
	
	Shareholder() {
	}
	
	public Shareholder(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
