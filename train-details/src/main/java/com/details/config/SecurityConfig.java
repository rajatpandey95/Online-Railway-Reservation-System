//package com.details.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//	
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		
//		httpSecurity
//				.authorizeHttpRequests()
//				.anyRequest().permitAll();
//		
//		return httpSecurity.build();
//	}
//	
//}
