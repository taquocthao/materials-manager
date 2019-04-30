package com.tathao.springmvc.config;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tathao.springmvc.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
//	@Autowired
//	private DataSource dataSource;
//	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		System.out.println("WebSecurityConfig::passwordEncoder()");
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		System.out.println("WebSecurityConfig::configure(AuthenticationManager )");
//		System.out.println("WebSecurityConfig:: using dataSource: " + this.dataSource);
//		
//		auth.jdbcAuthentication().dataSource(this.dataSource)
//		 	.usersByUsernameQuery("SELECT USER_NAME, ENCRYTED_PASSWORD, ENABLED " +
//		 			"FROM APP_USER WHERE USER_NAME = ?")
//		 	.authoritiesByUsernameQuery("SELECT USER_NAME, ROLE_NAME " +
//		 			"FROM APP_USER au, APP_ROLE ar, USER_ROLE ur " +
//		 			"WHERE ar.ROLE_ID = ur.ROLE_ID and au.USER_ID = ur.USER_ID and au.USER_NAME = ?")
//		 	.passwordEncoder(passwordEncoder());
//		 
//	}
	
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		
		return new MySimpleUrlAuthenticationSuccessHandler();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		System.out.println("WebSecurityConfig::configure(AuthenticationManager )");

		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		System.out.println("WebSecurityConfig::configure(HttpSecurity)");
		http.csrf().disable();
		
		http
			.authorizeRequests()
				.antMatchers("/login", "/logout").permitAll()
				.antMatchers("/branch/**").hasAnyRole("USER", "CHINHANH", "CONGTY")
				.antMatchers("/company/**").hasRole("CONGTY")
				.and()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
			.formLogin()
				.loginPage("/login")
				.successHandler(myAuthenticationSuccessHandler())
				.failureUrl("/login?error=true")
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");
				
		
	}
	
	
}
