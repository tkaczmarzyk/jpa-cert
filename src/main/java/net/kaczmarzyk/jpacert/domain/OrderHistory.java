package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderHistory {

	@Id @GeneratedValue
	private Long id;
	
	
	public Long getId() {
		return id;
	}
	
}
