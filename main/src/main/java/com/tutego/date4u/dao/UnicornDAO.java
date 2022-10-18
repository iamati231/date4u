package com.tutego.date4u.dao;

import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.entity.Unicorn;
import com.tutego.date4u.repository.UnicornRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnicornDAO {

	private final UnicornRepository unicornRepository;

	public UnicornDAO( UnicornRepository unicornRepository ) {
		this.unicornRepository = unicornRepository;
	}

	public Unicorn findById( Long id ) {
		Optional<Unicorn> unicorn = unicornRepository.findById( id );

		if( unicorn.isPresent() ) {
			return unicorn.get();
		}
		return null;
	}

	public Optional<Unicorn> findUnicornByEmail( String email ) {
		return unicornRepository.findUnicornByEmail( email );
	}

	public Boolean existsByEmail( String email ) {
		return unicornRepository.existsByEmail( email );
	}

	public Unicorn findByProfile( Profile profile ) {
		return unicornRepository.findByProfile( profile );
	}

	public void save( Unicorn unicorn ) {
		unicornRepository.save( unicorn );
	}
}
