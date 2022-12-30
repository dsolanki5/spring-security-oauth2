package com.way2learnonline;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// TODO-3  uncomment the below annotation to enable web layer security
//@EnableWebSecurity
public class SpringSecurityConfiguration {

	//TODO-4 uncomment the below to secure authorization endpoints of authorization server
	
	/*
	
	@Bean
	SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
		.formLogin(Customizer.withDefaults());
		
		return http.build();
		
	}
	*/
	
	
	

	@Bean
	public UserDetailsService users() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails user = User.withUsername("siva")
				.password(encoder.encode("secret"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
		
	}
	
	
	
	
}
