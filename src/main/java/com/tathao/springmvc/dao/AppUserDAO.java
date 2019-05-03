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
import org.springframework.transaction.annotation.Transactional;

import com.tathao.springmvc.mapper.AppUserMapper;
import com.tathao.springmvc.model.AppUser;


@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport {

	private static final String FIND_ACCOUNT_BY_USERNAME = "findAccountByUsername";

//	private DataSource dataSource;
	
	@Autowired
	public AppUserDAO(DataSource dataSource) {
//		this.dataSource = dataSource;
		this.setDataSource(dataSource);
		System.out.println("AppUserDAO::contructor() -> using "+ dataSource);
	}
	
	public AppUser findUserAccount(String username, String branchId) {
		
		System.out.println("AppUserDAO::datasource= " + this.getDataSource());
		
//		String sql = "SELECT au.USER_ID, au.MANV, au.USER_NAME, au.ENCRYTED_PASSWORD, au.ENABLED " + 
//				"FROM APP_USER au, NhanVien " + 
//				"WHERE au.MANV = nv.MANV and au.USER_NAME=? and nv.MACN=?";
//		
//		Object[] params = new Object[] { username, branchId };
//		AppUserMapper mapper = new AppUserMapper();
//		
//		AppUser appUser = this.getJdbcTemplate().queryForObject(sql, params, mapper);
//		
//		System.out.println("AppUserDAO:: find user account " + appUser.getUsername());
//		
//		return appUser;
		
		SimpleJdbcCall storedCall = new SimpleJdbcCall(this.getDataSource())
				.withProcedureName(FIND_ACCOUNT_BY_USERNAME);
		
		System.out.println("AppUserDAO:: declare instance storedCall");
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("USER_NAME_IN", username)
				.addValue("MACN", branchId);
		
		System.out.println("AppUserDAO:: execute param " + username + " - "+ branchId);
		
		Map<String, Object> rs = storedCall.execute(param);
//		
		if(rs.isEmpty()) {
			System.out.println("execute failure");
		} else {
			System.out.println("execute success");
			System.out.println("create instance account");
		}
		AppUser appUser = new AppUser();
		
		appUser.setUserId((long) rs.get("USER_ID"));
		appUser.setEmployeeId((int) rs.get("MANV"));
		appUser.setUsername((String) rs.get("USER_NAME"));
		appUser.setPassword((String)rs.get("PASSWORD"));
		appUser.setEnable((boolean) rs.get("ENABLED"));

		System.out.println("AppUserDAO:: username " + appUser.getUsername());
//		
		return appUser;
		
	}
}
