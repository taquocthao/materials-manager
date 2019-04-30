package com.tathao.springmvc.routing;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes())
	            .getRequest();
		String keyDS = (String)request	.getAttribute("keyDS");
		
		System.out.println("MyRoutingDataSource::determineCurrentLookupKey");
		System.out.println(keyDS);
		
		if(keyDS == null) {
			keyDS = "SERVER_ROOT";
		}
		return keyDS;
	}

}
