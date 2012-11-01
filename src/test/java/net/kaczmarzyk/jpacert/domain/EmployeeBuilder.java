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

import java.math.BigDecimal;

import net.kaczmarzyk.jpacert.service.CrudService;

public class EmployeeBuilder {

	private Employee employee;
	
	
	private EmployeeBuilder(String lastname) {
		employee = new Employee(lastname, BigDecimal.valueOf(1000));
	}
	
	public EmployeeBuilder withSalary(double salary) {
		employee.setSalary(BigDecimal.valueOf(salary));
		return this;
	}
	
	public EmployeeBuilder withCertificate(String certName) {
		Certificate cert = new Certificate(certName);
		employee.getCertificates().add(cert);
		cert.setEmployee(employee);
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

	public EmployeeBuilder withCertificates(String... certs) {
		for (String cert : certs) {
			withCertificate(cert);
		}
		return this;
	}
}
