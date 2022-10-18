package com.tutego.date4u.repository;

import com.tutego.date4u.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByNickname( String nickname );

	Boolean existsByNickname( String nickname );

	@Query( "SELECT p FROM Profile p WHERE p.nickname =:name" )
	Optional<Profile> findProfileByNickname( String name );

	@Query( "SELECT p FROM Profile p WHERE p.gender=:gender AND p.hornlength BETWEEN :min AND :max" )
	List<Profile> findProfileByGenderAndHornlength( byte gender, short min, short max );
}
