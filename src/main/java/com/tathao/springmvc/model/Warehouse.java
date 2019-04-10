package com.tathao.springmvc.model;

import javax.validation.constraints.NotEmpty;

public class Warehouse {
	
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;
	private String branch_id;
	
	public Warehouse() {
		
	}
	
	public Warehouse(String id, String name, String address, String branch_id) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.branch_id = branch_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
	
}
