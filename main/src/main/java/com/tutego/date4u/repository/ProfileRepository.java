package com.tutego.date4u.repository;

import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.entity.Unicorn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByNickname( String nickname );

	Boolean existsByNickname( String nickname );

	@Query( "SELECT p FROM Profile p WHERE p.nickname =:name" )
	Optional<Profile> findProfileByNickname( String name );

	Profile findByUnicorn( Unicorn unicorn );

	@Query( """
			SELECT p FROM Profile p
			WHERE  (p.birthdate  BETWEEN :minAge  AND :maxAge)
			   AND (p.hornlength BETWEEN :minHorn AND :maxHorn)
			   AND (p.gender = :gender) """ )
	Page<Profile> search( LocalDate minAge, LocalDate maxAge, short minHorn, short maxHorn, byte gender,
	                      Pageable pageable );
}
