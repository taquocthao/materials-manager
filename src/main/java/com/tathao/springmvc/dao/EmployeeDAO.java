package com.tathao.springmvc.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.tathao.springmvc.mapper.EmployeeMapper;
import com.tathao.springmvc.model.Employee;

@Repository
public class EmployeeDAO{
	
	static final String GET_ALL_EMPLOYEES = "usp_EmployeesGetAll";
	static final String GET_EMPLOYEES = "usp_EmployeeGetForEachPage";
	static final String GET_EMPLOYEE_BY_ID = "usp_EmployeeFindById";
	static final String ADD_EMPLOYEE = "usp_EmployeeAdd";
	static final String DELETE_EMPLOYEE = "usp_EmployeeDelete";
	static final String UPDATE_EMPLOYEE = "usp_EmployeeUpdate";
	
	private DataSource dataSource;
	
	@Autowired
	public EmployeeDAO(DataSource dataSource) {
		this.dataSource = dataSource;
		System.out.println("EmployeeDAO::Contructor() -> using DataSource: " + this.dataSource);
	}
	
	public Employee getEmployeeById(int id) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(GET_EMPLOYEE_BY_ID);	
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("MANV", id);
		
		Map<String, Object> out = simpleCall.execute(in);
		
		Employee e = new Employee();
		
		e.setId(id);
		e.setFirstname((String) out.get("TEN"));
		e.setLastname((String) out.get("HO"));
		e.setAddress((String) out.get("DIACHI"));
		e.setBirthday(out.get("NGAYSINH").toString());
		double salary = (Double) out.get("LUONG");
		e.setSalary((float) salary);
		e.setBranch_id((String) out.get("MACN"));

		return e;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> getEmployeesForEachPage(int start, int limit){
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(GET_EMPLOYEES)
				.returningResultSet("employees",new EmployeeMapper());
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("start", start)
				.addValue("limit", limit);
		
		Map<String, Object> out = simpleCall.execute(params);
		
		return (List<Employee>) out.get("employees");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees(){
	
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(GET_ALL_EMPLOYEES)
				.returningResultSet("employees", new EmployeeMapper());
		
		Map<String, Object> out = simpleCall.execute();
		
		List<Employee> list = (List<Employee>) out.get("employees");
		return list;
		
	}
	
	public boolean addEmployee(Employee e) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(dataSource)
				.withProcedureName(ADD_EMPLOYEE);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("HO", e.getLastname())
				.addValue("TEN", e.getFirstname())
				.addValue("DIACHI", e.getAddress())
				.addValue("NGAYSINH", e.getBirthday())
				.addValue("LUONG", e.getSalary())
				.addValue("MACN", e.getBranch_id());
		
		Map<String, Object> out =  simpleCall.execute(params);
		
		if(out.get("MANV") != null) {
			return  true;
		}
		return false;
	}
	
	public boolean deleteEmployee(int id) {
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(DELETE_EMPLOYEE);
		SqlParameterSource param = new MapSqlParameterSource().addValue("MANV", id);
		
		Map<String, Object> out = simpleCall.execute(param);
		int success = (Integer) out.get("SUCCESS");
		
		if(success == 1) {
			return true;
		}
		return false;
	}
	
	public boolean updateEmployee(Employee e) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(dataSource)
				.withProcedureName(UPDATE_EMPLOYEE);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("MANV", e.getId())
				.addValue("HO", e.getLastname())
				.addValue("TEN", e.getFirstname())
				.addValue("DIACHI", e.getAddress())
				.addValue("NGAYSINH", e.getBirthday())
				.addValue("LUONG", e.getSalary())
				.addValue("MACN", e.getBranch_id());
		
		Map<String, Object> out = simpleCall.execute(params);
		
		int success = (Integer) out.get("SUCCESS");
		
		if(success == 1) {
			return true;
		}
		return false;
	}
}
