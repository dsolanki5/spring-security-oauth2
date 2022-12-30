package com.way2learnonline;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;




@Configuration


public class WebSecurityConfiguration 
{
	
	



	
	
	//TODO-2 Uncomment the below beans to configure userdetails and password encoder
	
	
	/*
	@Bean
	public InMemoryUserDetailsManager userdetailsService() {

		return new InMemoryUserDetailsManager(User.withUsername("digestsiva").password(passwordEncoder().encode("secret")).roles("USER").build(),
				User.withUsername("admin").password(passwordEncoder().encode("secret")).roles("ADMIN").build());
	}
	
	*/
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
	

	
	
	// TODO-3 uncomment the below to configure credentials in database
	

	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	protected JdbcUserDetailsManager userdetailsService() {
		return new JdbcUserDetailsManager(dataSource);
	}	
	
	
	
	// TODO-4 Uncomment the below to add httpBasic authentication for your app
	
	
	@Bean
	@Order(2)
	SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
		/*
		
		http.authorizeRequests().anyRequest().authenticated();
		http.httpBasic();
	
		return http.build();
		*/
		
		
		http
		.authorizeRequests()
		.antMatchers("/h2-console/**","/mylogin").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/mylogin")			
			.and().csrf().disable();
		
		http.headers().disable();
		
		return http.build();

	}
	
	

	
	// TODO-5 uncomment the below to configure DigestAuthenticationEntryPoint
	
	private DigestAuthenticationEntryPoint getDigestEntryPoint() {
		DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
		digestEntryPoint.setRealmName("admin-digest-realm");
		digestEntryPoint.setKey("somedigestkey");
		return digestEntryPoint;
	}
	
	/*
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/webjars/**");
    }
    */

	

	
	

	// TODO-6 Uncomment the below to configure DigestAuthenticationFilter.
	// Observe the we are injecting userDetailsService and
	// DigestAuthenticationEntryPoint
	
	

	private DigestAuthenticationFilter getDigestAuthFilter() throws Exception {
		DigestAuthenticationFilter digestFilter = new DigestAuthenticationFilter();		
		digestFilter.setUserDetailsService(userdetailsService());	
		digestFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
		return digestFilter;
	}
	
	
	// TODO-7 uncomment the below to configure another SecurityFilterChain which uses DigestAuthenticationFilter

	
	
	@Bean
	@Order(1)
	public SecurityFilterChain myAdminSecurityFilterChain(HttpSecurity http) throws Exception {
		http.antMatcher("/admin/**")
			.addFilter(getDigestAuthFilter()).exceptionHandling()
				.authenticationEntryPoint(getDigestEntryPoint()).
				and()
				.authorizeRequests().antMatchers("/admin/**")
				.hasAuthority("ROLE_ADMIN")
		;
		//http.headers().disable();
		return http.build();
	}
	
	
	

		
	
	
	
}
