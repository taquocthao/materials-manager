package com.tathao.springmvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.tathao.springmvc.mapper.AppUserMapper;
import com.tathao.springmvc.model.AppUser;

@Repository
public class AppUserDAO extends JdbcDaoSupport {

	@Autowired
	public AppUserDAO(DataSource dataSource) {
		System.out.println("AppUserDAO::contructor() -> using "+ dataSource);
		this.setDataSource(dataSource);
		
	}
	
	public AppUser findUserAccount(String username) {
		
		String sql = "SELECT USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED "
				+ "FROM APP_USER "
				+ "WHERE USER_NAME=?";
		Object[] params = new Object[] { username };
		AppUserMapper mapper = new AppUserMapper();
		AppUser appUser = this.getJdbcTemplate().queryForObject(sql, params, mapper);
		System.out.println("AppUserDAO:: "+ appUser.getUsername());
		return appUser;
	}
}
