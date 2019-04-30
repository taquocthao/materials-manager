package com.tathao.springmvc.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebAppInitialzer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		System.out.println("SpringWebAppInitialzer:: onStartup()");
		
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(ApplicationContextConfig.class);

		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
		System.out.println("SpringWebAppInitialzer:: onStartup():dispatcher");
		
		// UtF8 Charactor Filter.
        FilterRegistration.Dynamic fr = 
        		servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
        
        System.out.println("SpringWebAppInitialzer:: onStartup():filterEncoding");
		
	}

}
