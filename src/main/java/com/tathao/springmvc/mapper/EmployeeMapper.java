package com.tathao.springmvc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tathao.springmvc.model.Employee;

public class EmployeeMapper implements RowMapper<Employee>{
	
	public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {	
		
		int id = resultSet.getInt(1);
		String lastname = resultSet.getString(2);
		String firstname = resultSet.getString(3);
		String address = resultSet.getString(4);
		String birthday = resultSet.getString(5);
		float salary = resultSet.getFloat(6);
		String branch_id = resultSet.getString(7);
		
		return new Employee(id, lastname, firstname, address, birthday, salary, branch_id);
	}

}
