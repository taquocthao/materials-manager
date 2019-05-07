package com.tathao.springmvc.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private static final long serialVersionUID = 1L;
	private final String branchId;
	
	public CustomUser(String username, String password, String branchId,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		// TODO Auto-generated constructor stub
		this.branchId = branchId;
	}
	
	public String getBranchId() {
		return this.branchId;
	}
	
}
