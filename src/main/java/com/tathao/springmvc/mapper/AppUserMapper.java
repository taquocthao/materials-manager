package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.AppUser;

public class AppUserMapper implements RowMapper<AppUser> {

	@Override
	public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		long userId = rs.getInt(1);
		int employeeId = rs.getInt(2);
		String username = rs.getString(3);
		String password = rs.getString(4);
		boolean isEnable = rs.getBoolean(5);
		
		return new AppUser(userId, employeeId ,username, password, isEnable);
	}

}
