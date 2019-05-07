package com.tathao.springmvc.config;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tathao.springmvc.security.MyAuthenticationFailureHandler;
import com.tathao.springmvc.security.MyAuthenticationFilter;
import com.tathao.springmvc.security.MyAuthenticationSuccessHandler;
import com.tathao.springmvc.security.MyUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsServiceImpl userDetailsService;

//	@Autowired
//	private MyUserDetailsAuthenticationProvider authProvider;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		System.out.println("WebSecurityConfig::passwordEncoder()");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MyAuthenticationFilter authenticationFilter() throws Exception {
		
		System.out.println("AuthenticationFilter:::");
		
		MyAuthenticationFilter filter = new MyAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler());
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler());
		return filter;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		System.out.println("AuthenticationProvider:::");
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		
		System.out.println("BEAN:: AuthenticationSuccessHandler");
		return new MyAuthenticationSuccessHandler();
		
	}
	
	@Bean
	public AuthenticationFailureHandler myAuthenticationFailureHandler() {
		return new MyAuthenticationFailureHandler();
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		System.out.println("WebSecurityConfig::configure(AuthenticationManager )");
//		auth.userDetailsService(userDetailsService)
//			.passwordEncoder(passwordEncoder());
		
//		auth.authenticationProvider(authProvider);
		
		auth.authenticationProvider(authenticationProvider());
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		System.out.println("WebSecurityConfig::configure(HttpSecurity)");
		http.csrf().disable();
		
		http
			.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
//			.antMatchers("/").permitAll();
				.antMatchers("/login", "/logout").permitAll()
				.antMatchers("/branch/**").hasAnyRole("USER", "CHINHANH", "CONGTY")
				.antMatchers("/company/**").hasRole("CONGTY")
				.and()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
//			.formLogin()
//				.loginPage("/login")
//				.successHandler(myAuthenticationSuccessHandler())
//				.failureUrl("/login?error=true")
//				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and()
			.rememberMe().key("uniqueAndSecret");
				
		
	}
	
	
}
