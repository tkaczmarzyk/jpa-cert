package net.kaczmarzyk.jpacert.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("time")
public class TimeAndMaterialProject extends Project {

	TimeAndMaterialProject() {
	}
	
	public TimeAndMaterialProject(String name) {
		super(name);
	}

}
