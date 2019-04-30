package com.tathao.springmvc.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository 
public class AppRoleDAO extends JdbcDaoSupport {

	@Autowired
	public AppRoleDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<String> getRoleNames(int userId){
		
		String sql = "SELECT ROLE_NAME "
				+ "FROM APP_ROLE ar, USER_ROLE ur "
				+ "WHERE ar.ROLE_ID = ur.ROLE_ID and ur.USER_ID=?";
		
		Object[] params = new Object[] {userId};
		
		List<String> list = this.getJdbcTemplate().queryForList(sql, params, String.class);
		
		return list;
	}
	
}
