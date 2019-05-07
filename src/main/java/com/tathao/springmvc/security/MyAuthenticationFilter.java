package com.tathao.springmvc.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
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
		String branchId = request.getParameter("branch").trim();
		
		System.out.println("AUTHENTICATION_FILTER::getAuthenRequest()");
		
		if(branchId.equals("CN1")) {
			request.setAttribute("keyDS", "SERVER_1");
			System.out.println("set keyDS =  Server_1");
		} else if(branchId.equals("CN2")) {
			request.setAttribute("keyDS", "SERVER_2");
			System.out.println("set keyDS =  Server_2");
		}
			
		System.out.println("Get param form authentication filter " + username);
		System.out.println("Get param form authentication filter " + password);
		System.out.println("Get param form authentication filter " + branchId);
		
		String usernameAndBranchId = String.format("%s%s%s", username.trim(), 
		          String.valueOf(Character.LINE_SEPARATOR), branchId.trim());
		
		System.out.println("Get param form authentication filter " + usernameAndBranchId);
		
		return new UsernamePasswordAuthenticationToken(usernameAndBranchId, password);
		
	}
	
}
