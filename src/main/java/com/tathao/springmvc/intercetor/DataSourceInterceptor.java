package com.tathao.springmvc.intercetor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class DataSourceInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		String contextPath = request.getServletContext().getContextPath();
		
		//branch 1
		String prefixBranch1 = contextPath + "/branch/1";
		
		//branch 2
		String prefixBranch2 = contextPath + "/branch/2";
		
		// root
		String prefixBranch = contextPath;
		
		String uri = request.getRequestURI();
		
		System.out.println("DataSourceInterceptor");
		System.out.println("URI: " + uri);
		
		if(uri.startsWith(prefixBranch)) {
			
			request.setAttribute("keyDS", "SERVER_ROOT");
			
		} else if(uri.startsWith(prefixBranch1)) {
			
			request.setAttribute("keyDS", "SERVER_1");
			
		} else if(uri.startsWith(prefixBranch2)) {
			
			request.setAttribute("keyDS", "SERVER_2");
		}
			
		return true;
	}
	
}
