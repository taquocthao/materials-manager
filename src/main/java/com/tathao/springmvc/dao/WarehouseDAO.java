package com.tathao.springmvc.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.tathao.springmvc.mapper.WarehouseMapper;
import com.tathao.springmvc.model.Warehouse;

@Repository
public class WarehouseDAO extends JdbcDaoSupport{
	
	private static final String WAREHOUSE_GET_ALL = "usp_WarehouseGetAll";
	private static final String WAREHOUSE_ADD = "usp_WarehouseAdd";
	private static final String WAREHOUSE_UPDATE = "usp_WarehouseUpdate";
	private static final String WAREHOUSE_DELETE = "usp_WarehouseDelete";
	
	private DataSource dataSource;
	
	public WarehouseDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
	
	@SuppressWarnings("unchecked")
	public List<Warehouse> getAll(int start, int limit){
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(WAREHOUSE_GET_ALL)
				.returningResultSet("listWarehouse", new WarehouseMapper());
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("start", start)
				.addValue("limit", limit);
		
		Map<String, Object> out = simpleCall.execute(params);
		
		return (List<Warehouse>) out.get("listWarehouse");
	}
	
	public boolean add(Warehouse w) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(WAREHOUSE_ADD);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("TENKHO", w.getName())
				.addValue("DIACHI", w.getAddress())
				.addValue("MACN", w.getBranch_id());
		
		Map<String, Object> out = simpleCall.execute(params);
		
		int success = (Integer) out.get("IsSuccess");
		
		if(success == 1) {
			return true;
		}
		return false;	
	}
	
	public boolean update(Warehouse w) {

		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource).withProcedureName(WAREHOUSE_UPDATE);

		SqlParameterSource params = new MapSqlParameterSource().addValue("MAKHO", w.getId())
				.addValue("TENKHO", w.getName()).addValue("DIACHI", w.getAddress()).addValue("MACN", w.getBranch_id());

		Map<String, Object> out = simpleCall.execute(params);

		int success = (Integer) out.get("IsSuccess");

		if (success == 1) {
			return true;
		}
		return false;
	}
	
	public boolean delete(String id) {

		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource).withProcedureName(WAREHOUSE_DELETE);

		SqlParameterSource param = new MapSqlParameterSource().addValue("MAKHO", id);
		

		Map<String, Object> out = simpleCall.execute(param);

		int success = (Integer) out.get("IsSuccess");

		if (success == 1) {
			return true;
		}
		return false;
	}
}


