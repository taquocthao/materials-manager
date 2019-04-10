package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.Warehouse;

public class WarehouseMapper implements RowMapper<Warehouse> {

	public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		String id = rs.getString(1);
		String name = rs.getString(2);
		String address = rs.getString(3);
		String branch_id = rs.getString(4);
		
		return new Warehouse(id, name, address, branch_id);
	}

}
