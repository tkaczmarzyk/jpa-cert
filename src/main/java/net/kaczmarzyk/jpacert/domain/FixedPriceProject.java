package net.kaczmarzyk.jpacert.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("fixed")
public class FixedPriceProject extends Project {

	FixedPriceProject() {
	}
	
	public FixedPriceProject(String name) {
		super(name);
	}

}
