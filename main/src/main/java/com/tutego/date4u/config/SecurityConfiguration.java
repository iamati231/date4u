package com.tutego.date4u.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true, securedEnabled = true )
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceConfiguration();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig ) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService( userDetailsService() );

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {

		http
				.authorizeRequests()
				.antMatchers( "/index" ).permitAll()
				.antMatchers( "/" ).permitAll()
				.antMatchers( "/register" ).permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage( "/login" ).usernameParameter( "email" )
				.defaultSuccessUrl( "/index", true )
				.permitAll()
				.and()
				.logout()
				.invalidateHttpSession( true )
				.logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
				.logoutSuccessUrl( "/login" )
				.deleteCookies( "JSESSIONID" )
				.permitAll()
				.and()
				.httpBasic();

		http.authenticationProvider( authenticationProvider() );
		http.headers().frameOptions().sameOrigin();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return ( web ) -> web.ignoring().antMatchers( "/css/**", "/js/**", "/images/**" );
	}

}