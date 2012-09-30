package net.kaczmarzyk.jpacert.domain;

import java.math.BigDecimal;

import net.kaczmarzyk.jpacert.service.CrudService;

public class EmployeeBuilder {

	private Employee employee;
	
	
	private EmployeeBuilder(String lastname) {
		employee = new Employee(lastname, BigDecimal.valueOf(5000));
	}
	
	public EmployeeBuilder withSalary(double salary) {
		employee.setSalary(BigDecimal.valueOf(salary));
		return this;
	}
	
	public EmployeeBuilder withPhoneNum(PhoneType type, String num) {
		employee.setPhoneNumber(type, num);
		return this;
	}
	
	public Employee build() {
		return employee;
	}
	
	public static EmployeeBuilder anEmployee(String lastname, CrudService crud) {
		EmployeeBuilder builder = new EmployeeBuilder(lastname);
		crud.persist(builder.employee);
		return builder;
	}
}
