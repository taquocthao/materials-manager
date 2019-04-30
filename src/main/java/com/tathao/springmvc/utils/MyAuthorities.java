package com.tathao.springmvc.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class MyAuthorities {

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_BRANCH = "ROLE_CHINHANH";
	public static final String ROLE_COMPANY= "ROLE_CONGTY";
	
	public static boolean hasRole(String roleName) {
		
		return SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
		
	}
	
}
