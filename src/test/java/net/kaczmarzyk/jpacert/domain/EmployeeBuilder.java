package net.kaczmarzyk.jpacert.domain;

import net.kaczmarzyk.jpacert.service.CrudService;

public class EmployeeBuilder {

	
	public static Employee anEmployee(String lastname, CrudService crud) {
		Employee e = new Employee(lastname);
		crud.persist(e);
		return e;
	}
}
