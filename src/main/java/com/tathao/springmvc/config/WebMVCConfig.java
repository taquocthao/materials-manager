package com.tathao.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tathao.springmvc.intercetor.DataSourceInterceptor;

@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// css
		registry.addResourceHandler("/styles/**").addResourceLocations("/WEB-INF/resources/css/");

		// javascript
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/resources/js/");

		// jquery
		registry.addResourceHandler("/image/**").addResourceLocations("/WEB-INF/resources/image/");
		
		System.out.println("WebMVCConfig:: addResourceHandlers()");

	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		System.out.println("WebMVCConfig:: addInterceptors()");
		registry.addInterceptor(new DataSourceInterceptor());
	}

}
