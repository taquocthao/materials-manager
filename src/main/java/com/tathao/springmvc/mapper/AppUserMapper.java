package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.AppUser;

public class AppUserMapper implements RowMapper<AppUser> {

	@Override
	public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int userId = rs.getInt(1);
//		int employeeId = rs.getInt(2);
		String username = rs.getString(2);
		String password = rs.getString(3);
		boolean isEnable = rs.getBoolean(4);
		
		return new AppUser(userId, 1 ,username, password, isEnable);
	}

}
