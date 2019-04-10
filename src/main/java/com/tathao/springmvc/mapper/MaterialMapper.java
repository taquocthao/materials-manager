package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.Material;

public class MaterialMapper implements RowMapper<Material> {

	public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		String id = rs.getString(1);
		String name = rs.getString(2);
		String unit = rs.getString(3);
		
		return new Material(id, name, unit);
	}
	
}
