package com.tathao.springmvc.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.tathao.springmvc.mapper.MaterialMapper;
import com.tathao.springmvc.model.Material;

@Repository

public class MaterialDAO extends JdbcDaoSupport{
	
	static final String MATERIALS_GET = "usp_MaterialsGetForEachPage";
	static final String MATERIAL_ADD = "usp_MaterialAdd";
	static final String MATERIAL_UPDATE = "usp_MaterialUpdate";
	static final String MATERIAL_DELETE = "usp_MaterialDelete";
	
	private DataSource dataSource;
	
	@Autowired
	public MaterialDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
	
	@SuppressWarnings("unchecked")
	public List<Material> getListForEachPage(int start, int limit){
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(MATERIALS_GET)
				.returningResultSet("materials", new MaterialMapper());
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("start", start)
				.addValue("limit", limit);
		
		Map<String, Object> out = simpleCall.execute(params);
		List<Material> list = (List<Material>) out.get("materials");
		
		return list;
	}
	
	public boolean add(Material m) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(dataSource)
				.withProcedureName(MATERIAL_ADD);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("TENVT", m.getName())
				.addValue("DVT", m.getUnit());
		
		Map<String, Object> out = simpleCall.execute(params);
		
		int addSuccess = (Integer) out.get("IsSuccess");
		if(addSuccess == 1) {
			return true;
		}
		return false;
	}
	
	public boolean update(Material m) {
		
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(dataSource)
				.withProcedureName(MATERIAL_UPDATE);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("MAVT", m.getId())
				.addValue("TENVT", m.getName())
				.addValue("DVT", m.getUnit());
		
		Map<String, Object> out = simpleCall.execute(params);
		
		int updateSuccess = (Integer) out.get("IsSuccess");
		if(updateSuccess == 1) {
			return true;
		}
		return false;
	}
	
	public boolean delete(String id) {
		SimpleJdbcCall simpleCall = new SimpleJdbcCall(this.dataSource)
				.withProcedureName(MATERIAL_DELETE);
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("MAVT", id);
		
		Map<String, Object> out = simpleCall.execute(param);
		
		int isSuccess = (Integer) out.get("IsSuccess");
		
		if(isSuccess == 1) {
			return true;
		}
		return false;
	}
	
}
