package com.tathao.springmvc.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class Employee {
	
	private int id;
	@NotEmpty
	@Size(min=1, max=40)
	private String firstname;
	@NotEmpty
	@Size(min=1, max=40)
	private String lastname;
	@NotEmpty
	@Size(max=40)
	private String address;
	@NotEmpty
	private String birthday;
	@NotNull
	@Min(800000)
	private float salary;
	@NotEmpty
	private String branch_id;
	
	public Employee() {
		
	}
	
	public Employee(int id, String lastname, String firstname , String address, String birthday, float salary,
			String branch_id) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.birthday = birthday;
		this.salary = salary;
		this.branch_id = branch_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
	
	
	
}
