package com.tathao.springmvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("AuthenticationFilter::attemptAuthentication()");
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);
		return this.getAuthenticationManager()
				.authenticate(authRequest);
		
	}

	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String branchId = request.getParameter("branch");
		
		System.out.println("Get param form authentication filter " + username);
		System.out.println("Get param form authentication filter " + password);
		System.out.println("Get param form authentication filter " + branchId.trim());
		
		String usernameAndBranchId = String.format("%s%s%s", username.trim(), 
		          String.valueOf(Character.LINE_SEPARATOR), branchId.trim());
		
		System.out.println("Get param form authentication filter " + usernameAndBranchId);
		
		return new UsernamePasswordAuthenticationToken(usernameAndBranchId, password);
		
	}
	
}
