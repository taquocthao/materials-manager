package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.Branch;

public class BranchMapper implements RowMapper<Branch> {

	public static final String SQL_SELECT = "select * from chinhanh";


	public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {

		String id = rs.getString(1);
		String branch = rs.getString(2);
		String address = rs.getString(3);
		String phone = rs.getString(4);
		return new Branch(id, branch, address, phone);
	}

}
