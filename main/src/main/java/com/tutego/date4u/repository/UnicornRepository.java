package com.tutego.date4u.repository;

import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.entity.Unicorn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnicornRepository extends JpaRepository<Unicorn, Long> {

	Optional<Unicorn> findUnicornByEmail( String email );

	Boolean existsByEmail( String email );

	Unicorn findByProfile( Profile profile );
}
