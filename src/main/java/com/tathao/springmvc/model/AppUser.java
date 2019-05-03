package com.tathao.springmvc.model;

public class AppUser {

	private long userId;
	private int employeeId;
	private String username;
	private String password;
	private boolean enabled;
	
	public AppUser() {
		
	}
	
	public AppUser(long userID, int employeeID, String username, String password, 
			boolean enabled) {
		super();
		this.userId = userID;
		this.employeeId = employeeID;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userID) {
		this.userId = userID;
	}

	public int getEmployeeID() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnable() {
		return enabled;
	}
	
	public void setEnable(boolean enabled) {
		this.enabled = enabled;
	}
}
