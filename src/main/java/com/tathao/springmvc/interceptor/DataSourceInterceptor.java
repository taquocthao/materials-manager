package com.tathao.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class DataSourceInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		String contextPath = request.getServletContext().getContextPath();
		
		//branch 1
		String prefixBranch1 = contextPath + "/branch/1/";
		
		//branch 2
		String prefixBranch2 = contextPath + "/branch/2/";
		
//		root
//		String prefixBranch = contextPath + "/company/";
		
		String uri = request.getRequestURI();
		
		System.out.println("DataSourceInterceptor");
		System.out.println("URI: " + uri);
		
		if(uri.startsWith(prefixBranch1)) {
			
			request.setAttribute("keyDS", "SERVER_1");
			
		} else if(uri.startsWith(prefixBranch2)) {
			
			request.setAttribute("keyDS", "SERVER_2");
		} else {
			
			try {
				
				String keyDS = request.getAttribute("keyDS").toString();
				if(!keyDS.equals("") || !keyDS.isEmpty()) {
					System.out.println("DataSourceInterceptor:: keyDS" + keyDS);
					request.setAttribute("KeyDS", keyDS );
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("");
			}
			
			
		}
			
		return true;
	}
	
}
