package com.sneha.socialmediademo.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//1. All the requests should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		//2. If a request is not authenticated, a web page must be shown 
		http.httpBasic(withDefaults());
		
		//3. Disable CSRF -> Or else POST, PUT won't work 
		http.csrf().disable();
		
		return http.build();
	}
}
