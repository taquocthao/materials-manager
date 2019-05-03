package com.tathao.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//@Component
public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		System.out.println("AuthenticationProvider:: additionalAuthenticationChecks()");
		System.out.println("AuthenticationProvider:: UserDetails : password = " + userDetails.getPassword());
		System.out.println("AuthenticationProvider:: AuthenticationToken : password = " + authentication.getCredentials().toString());
//		if(!(authentication.getCredentials().toString().equals(userDetails.getPassword()))) {
//			System.out.println("password invalid");
//			throw new BadCredentialsException("password could not be validated");
//		}
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		System.out.println("AuthenticationProvider:: retrieveUser()");
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if(userDetails == null) {
			throw new UsernameNotFoundException("User could not be loaded");
		}
		
		return userDetails;
	}
}
