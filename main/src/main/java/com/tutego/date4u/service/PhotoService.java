package com.tutego.date4u.service;

import com.tutego.date4u.core.FileSystem;
import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Validated
public class PhotoService {

	private final Logger log = LoggerFactory.getLogger( getClass() );

	private final FileSystem fs;
	private final Thumbnail thumbnail;
	private final ProfileDAO profileDAO;
	private final PhotoDAO photoDAO;

	public PhotoService( FileSystem fs, Thumbnail thumbnail, ProfileDAO profileDAO, PhotoDAO photoDAO ) {
		this.fs = fs;
		this.thumbnail = thumbnail;
		this.profileDAO = profileDAO;
		this.photoDAO = photoDAO;
	}

	@Cacheable( "date4u.filesystem.file" )
	public Optional<byte[]> download( String name ) {
		try {
			return Optional.of( fs.load( name + ".jpg" ) );
		} catch( UncheckedIOException e ) {
			return Optional.empty();
		}
	}

	@Cacheable( cacheNames = "date4u.filesystem.file", key = "#photo.name" )
	public Optional<byte[]> download( @Valid Photo photo ) {
		return download( photo.getName() );
	}

	public String upload( byte[] imageBytes ) {
		Future<byte[]> thumbnailBytes = thumbnail.thumbnail( imageBytes );
		String imageName = UUID.randomUUID().toString();
		fs.store( imageName + ".jpg", imageBytes );

		return imageName;
	}

	public String uploadPhoto( Long id, MultipartFile mpFile ) throws IOException {
		Optional<Profile> profile = profileDAO.findById( id );

		if( ! profile.isPresent() ) {
			return null;
		}

		byte[] bytes = mpFile.getBytes();
		String name = upload( bytes );

		Photo photo = new Photo();
		photo.setProfile( profile.get() );
		photo.setName( name );
		photo.setProfilePhoto( false );
		photo.setCreated( LocalDateTime.now().truncatedTo( ChronoUnit.SECONDS ) );

		profile.get().add( photo );
		photoDAO.save( photo );

		return photo.getName();
	}
}