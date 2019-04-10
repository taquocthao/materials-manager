package com.tathao.springmvc.model;

public class Branch {
	
	private String id;
	private String branch;
	private String address;
	private String numberphone;
	
	public Branch(String id, String branch, String address, String numberphone) {
		super();
		this.id = id;
		this.branch = branch;
		this.address = address;
		this.numberphone = numberphone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumberphone() {
		return numberphone;
	}

	public void setNumberphone(String numberphone) {
		this.numberphone = numberphone;
	}
	
}
