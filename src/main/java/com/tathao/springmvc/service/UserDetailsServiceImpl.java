package com.tathao.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
		
		String[] usernameAndBranchId = StringUtils.split(username, String.valueOf(Character.LINE_SEPARATOR));
		
		if(usernameAndBranchId == null || usernameAndBranchId.length != 2) {
			throw new UsernameNotFoundException("Username and branch must be provied");
		}
		
		System.out.println("USER_DETAILS_SERVICE: get username: " + username);
		System.out.println("USER_DETAILS_SERVICE: separate: username: " + usernameAndBranchId[0]);
		System.out.println("USER_DETAILS_SERVICE: separate: branch: " + usernameAndBranchId[1]);
		
		System.out.println("USER_DETAILS_SERVICE:: find user account by user and branch");
		AppUser appUser = new AppUser();
		appUser = appUserDAO.findUserAccount(usernameAndBranchId[0], usernameAndBranchId[1]);
		
		if(appUser == null) {
			System.out.println("account null");
			throw new UsernameNotFoundException("User "+ username + " not found in branch ");
		}
		System.out.println("has account!... finding role account....");
		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(roleNames != null) {
			for(String role : roleNames) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
				grantList.add(grantedAuthority);
			}
		}
		System.out.println("USER_DETAILS_SERVICE_IMPL:: create instance User");
		System.out.println("USER_DETAILS_SERVICE_IMPL:: username = " + appUser.getUsername());
		System.out.println("USER_DETAILS_SERVICE_IMPL:: password = " + appUser.getPassword());
		System.out.println("USER_DETAILS_SERVICE_IMPL:: branch = " + usernameAndBranchId[1]);
		System.out.println("USER_DETAILS_SERVICE_IMPL:: granted = ");
		CustomUser userDetails = new CustomUser(appUser.getUsername(), appUser.getPassword(), usernameAndBranchId[1],
				grantList);
		
		System.out.println("User Details : " + userDetails.getUsername());
		
		return userDetails;
		
		
//		System.out.println("UserDetailsService::");
//		
//		AppUser appUser = appUserDAO.findUserAccount(username);
//		
//		if(appUser == null) {
//			System.out.println("User not found!");
//			throw new UsernameNotFoundException("User "+ username + " not found in database");
//		}
//		
//		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
//		
//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		
//		if(roleNames != null) {
//			for(String role : roleNames) {
//				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
//				grantList.add(grantedAuthority);
//			}
//		}
//		
//		UserDetails userDetails = new User(appUser.getUsername(), appUser.getPassword(),
//				grantList);
//		
//		System.out.println("User Details : " + userDetails.getUsername());
//		
//		return userDetails;
		
	}

}
