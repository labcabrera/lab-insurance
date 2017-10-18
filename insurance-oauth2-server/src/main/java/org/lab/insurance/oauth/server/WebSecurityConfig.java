package org.lab.insurance.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// TODO mongodb
	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
		auth.inMemoryAuthentication()
		  .withUser("user1").password("user1").roles("USER").and()
		  .withUser("user2").password("user2").roles("USER").and()
		  .withUser("admin1").password("admin1").roles("ADMIN").and()
		  .withUser("admin2").password("admin2").roles("ADMIN");
    } // @formatter:on

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception { // @formatter:off
		http.authorizeRequests()
			.antMatchers("/login")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.permitAll();
	} //@formatter:on

}
