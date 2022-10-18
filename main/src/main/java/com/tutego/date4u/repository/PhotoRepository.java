package com.tutego.date4u.repository;

import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

	@Query( "SELECT p FROM Photo p WHERE p.profile =:profile" )
	List<Photo> findByProfile( Profile profile );

	@Query( "SELECT p FROM Photo p WHERE p.profile =:profile AND p.isProfilePhoto" )
	Photo findByProfilePhoto( Profile profile );

	@Query( "SELECT p FROM Photo p WHERE p.name =:name" )
	Photo findPhotoByName( String name );

}