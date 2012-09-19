package net.kaczmarzyk.jpacert.example;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;


@Stateless
@LocalBean
public class HelloBean {

	public String hello() {
		return "Hello, World!";
	}
}
