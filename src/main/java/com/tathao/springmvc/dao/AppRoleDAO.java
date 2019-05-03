package com.tathao.springmvc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class AppRoleDAO extends JdbcDaoSupport{

	private static final String GET_ROLE_NAME = "getRoleNames";
//	private DataSource dataSource;
	
	@Autowired
	public AppRoleDAO(DataSource dataSource) {
		System.out.println("AppRoleDAO::contructor() -> using datasource " + dataSource);
//		this.dataSource = dataSource;
		this.setDataSource(dataSource);
	}
	
	public List<String> getRoleNames(long userId){
//		
//		String sql = "SELECT ROLE_NAME "
//				+ "FROM APP_ROLE ar, USER_ROLE ur "
//				+ "WHERE ar.ROLE_ID = ur.ROLE_ID and ur.USER_ID=?";
//		
//		Object[] params = new Object[] {userId};
//		
//		List<String> list = this.getJdbcTemplate().queryForList(sql, params, String.class);
//		
//		return list;
//		

		SimpleJdbcCall storedCall = new SimpleJdbcCall(this.getDataSource())
										.withProcedureName(GET_ROLE_NAME);

		SqlParameterSource param = new MapSqlParameterSource().addValue("USER_ID", userId);
		
		Map<String, Object> out = storedCall.execute(param);
		
		List<String> list = new ArrayList<String>();
		
		out.forEach((key, value) -> {
			list.add(value.toString());
			System.out.println("value role: " + value);
		});
		
		return list;
	
	}
}
