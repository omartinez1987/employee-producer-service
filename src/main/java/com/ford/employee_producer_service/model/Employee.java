package com.ford.employee_producer_service.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data // genera getters, setters, toString, equals, hashCode
//@NoArgsConstructor // genera constructor vacío
//@AllArgsConstructor // genera constructor con todos los campos
public class Employee {

    private String name;
    private Integer age;
    private String position;
    
    public Employee() {}
    
	public Employee(String name, Integer age, String position) {
		super();
		this.name = name;
		this.age = age;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
    
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Employee employee = (Employee) o;
	    return age == employee.age &&
	            Objects.equals(name, employee.name) &&
	            Objects.equals(position, employee.position);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(name, age, position);
	}

}
// check and uncked exception