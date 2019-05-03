package com.tathao.springmvc.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.tathao.springmvc.mapper.BranchMapper;
import com.tathao.springmvc.model.Branch;

@Repository
public class BranchDAO{

	static final String GET_BRANCHS = "usp_GetBranchs";
	
	private DataSource dataSource;
	
	@Autowired
	public BranchDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;		
//		System.out.println("BranchDAO::Contructor() -> using DataSource: " + dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List<Branch> getAllBranchs(){
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(GET_BRANCHS)
				.returningResultSet("branchs", new BranchMapper());
		
		Map<String, Object> out =  simpleCall.execute();
		
		return (List<Branch>) out.get("branchs");
	}
	
}
