package com.tutego.date4u.config;

import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.entity.Unicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class UserDetailsServiceConfiguration implements UserDetailsService {

	@Autowired
	private UnicornDAO unicornDAO;

	@Override
	public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
		Optional<Unicorn> unicorn = unicornDAO.findUnicornByEmail( email );

		if( ! unicorn.isPresent() ) {
			throw new UsernameNotFoundException( "User not found " + email );
		}
		return new org.springframework.security.core.userdetails.User(
				unicorn.get().getEmail(),
				unicorn.get().getPassword(),
				Collections.emptyList() );
	}
}
