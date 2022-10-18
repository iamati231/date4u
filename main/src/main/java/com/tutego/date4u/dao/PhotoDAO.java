package com.tutego.date4u.dao;

import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoDAO {

	private final PhotoRepository photoRepository;

	public PhotoDAO( PhotoRepository photoRepository ) {
		this.photoRepository = photoRepository;
	}

	public List<Photo> findByProfile( Profile profile ) {
		return photoRepository.findByProfile( profile );
	}

	public Photo findByProfilePhoto( Profile profile ) {
		return photoRepository.findByProfilePhoto( profile );
	}

	public Photo findPhotoByName( String name ) {
		return photoRepository.findPhotoByName( name );
	}

	public void save( Photo photo ) {
		photoRepository.save( photo );
	}

	public void update( Profile profile, String photoName ) {
		Photo oldProfilePhoto = findByProfilePhoto( profile );
		oldProfilePhoto.setProfile( oldProfilePhoto.getProfile() );
		oldProfilePhoto.setProfilePhoto( false );
		oldProfilePhoto.setName( oldProfilePhoto.getName() );
		oldProfilePhoto.setCreated( oldProfilePhoto.getCreated() );

		save( oldProfilePhoto );

		Photo newProfilePhoto = findPhotoByName( photoName );
		newProfilePhoto.setProfilePhoto( true );
		newProfilePhoto.setCreated( newProfilePhoto.getCreated() );
		newProfilePhoto.setProfile( newProfilePhoto.getProfile() );
		newProfilePhoto.setName( newProfilePhoto.getName() );

		save( newProfilePhoto );
	}

}
