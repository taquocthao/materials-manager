package com.tathao.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tathao.springmvc.dao.AppRoleDAO;
import com.tathao.springmvc.dao.AppUserDAO;
import com.tathao.springmvc.model.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserDAO appUserDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("UserDetailsService::");
		
		AppUser appUser = appUserDAO.findUserAccount(username);
		
		if(appUser == null) {
			System.out.println("User not found!");
			throw new UsernameNotFoundException("User "+ username + " not found in database");
		}
		
		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(roleNames != null) {
			for(String role : roleNames) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
				grantList.add(grantedAuthority);
			}
		}
		
		UserDetails userDetails = new User(appUser.getUsername(), appUser.getPassword(),
				grantList);
		
		System.out.println("User Details : " + userDetails.getUsername());
		
		return userDetails;
		
	}

}
