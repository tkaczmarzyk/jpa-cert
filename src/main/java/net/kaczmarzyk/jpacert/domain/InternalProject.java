package net.kaczmarzyk.jpacert.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("internal")
public class InternalProject extends Project {

	InternalProject() {
	}
	
	public InternalProject(String name) {
		super(name);
	}

}
