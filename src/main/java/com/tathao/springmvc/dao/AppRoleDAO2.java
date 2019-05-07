package com.tathao.springmvc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


@Repository("appRoleDAO2")
public class AppRoleDAO2{

	private static final String GET_ROLE_NAME = "getRoleNames";

	@Autowired
	@Qualifier("dataSource2")
	private DataSource dataSource;
	
	
	public List<String> getRoleNames(long userId){

		SimpleJdbcCall storedCall = new SimpleJdbcCall(this.dataSource)
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
