package com.tathao.springmvc.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.tathao.springmvc.interceptor.MyRoutingDataSource;

@Configuration
@ComponentScan("com.tathao.springmvc.*")
@EnableTransactionManagement
@PropertySources({@PropertySource("classpath:ds/mssql-cfg.properties")})
public class ApplicationContextConfig {
	
	@Autowired
	private Environment env;
	
	@Bean("viewResolver")
	public ViewResolver getViewResolver() {
		System.out.println("ApplicationContextConfig:: ViewResolver()");
		
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}
	
	@Bean("tilesConfigurer")
	public TilesConfigurer getTilesConfigurer() {
		
		System.out.println("ApplicationContextConfig:: getTilesConfigurer()");
		
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
		return tilesConfigurer;
	}
	
	@Autowired
	@Bean(name="dataSource")
	public DataSource getDataSource(DataSource dataSource1, DataSource dataSource2
			, DataSource dataSource3) {
		
		System.out.println("ApplicationContextConfig:: getDataSource(datasource....)");
		
		MyRoutingDataSource dataSource = new MyRoutingDataSource();
		Map<Object, Object> dsMap = new HashMap<Object, Object>();
		dsMap.put("SERVER_ROOT", dataSource1);
		dsMap.put("SERVER_1", dataSource2);
		dsMap.put("SERVER_2", dataSource3);
		
		dataSource.setTargetDataSources(dsMap);
		
		System.out.println("ApplicationContextConfig:: datasource "+ dataSource);
		
		
		return dataSource;
	}
	
	@Bean(name="dataSource1")
	public DataSource getDataSource1() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("ds.database-driver1"));
		dataSource.setUrl(env.getProperty("ds.url1"));
		dataSource.setUsername(env.getProperty("ds.username1"));
		dataSource.setPassword(env.getProperty("ds.password1"));	
		
		System.out.println("ApplicationContextConfig: GET DATA SOURCE 1"   + dataSource);
		
		return dataSource;
	}
	
	@Bean(name="dataSource2")
	public DataSource getDataSource2() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("ds.database-driver2"));
		dataSource.setUrl(env.getProperty("ds.url2"));
		dataSource.setUsername(env.getProperty("ds.username2"));
		dataSource.setPassword(env.getProperty("ds.password2"));
		
		System.out.println("ApplicationContextConfig: GET DATA SOURCE 2"   + dataSource);
		
		return dataSource;
	}
	
	@Bean(name="dataSource3")
	public DataSource getDataSource3() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("ds.database-driver3"));
		dataSource.setUrl(env.getProperty("ds.url3"));
		dataSource.setUsername(env.getProperty("ds.username3"));
		dataSource.setPassword(env.getProperty("ds.password3"));
		
		System.out.println("ApplicationContextConfig : GET DATA SOURCE 3"   + dataSource);
		
		return dataSource;
	}

	@Autowired
	@Bean(name="transactionManager")
	public DataSourceTransactionManager geTransactionManager(DataSource dataSource) {
		
		System.out.println("ApplicationContextConfig:: getTransactionManager()");
		System.out.println("getTransactionManager()::datasource = "+ dataSource);
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource);
		
		return txManager;
	}
	
	@Bean("messageResource")
	public ReloadableResourceBundleMessageSource messageSource() {
		
		System.out.println("ApplicationContextConfig:: messageSource()");
		
		ReloadableResourceBundleMessageSource messageSource = 
				new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
	
}
