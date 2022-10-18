package com.tutego.date4u.service;

import com.github.javafaker.Faker;
import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.dto.UnicornDTO;
import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.entity.Unicorn;
import com.tutego.date4u.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger( AuthService.class );
	private final UnicornDAO unicornDAO;
	private final ProfileDAO profileDAO;
	private final PhotoDAO photoDAO;
	private Faker faker = new Faker();
	private final PhotoService photoService;

	public AuthService( UnicornDAO unicornDAO, ProfileDAO profileDAO, PhotoDAO photoDAO, PhotoService photoService ) {
		this.unicornDAO = unicornDAO;
		this.profileDAO = profileDAO;
		this.photoDAO = photoDAO;
		this.photoService = photoService;
	}

	public void register( UnicornDTO unicornDTO ) throws IOException {

		if( unicornDAO.existsByEmail( unicornDTO.getEmail() ) ) {
			log.info( "already registered with email",
					new Exception( "already registered with email " + unicornDTO.getEmail() ) );
		}

		Unicorn unicorn = new Unicorn();
		Profile profile = new Profile();
		Photo photo = new Photo();

		profile.setNickname( faker.harryPotter().character().replaceAll( "\\s+", "" ) );
		profile.setBirthdate( LocalDate.now() );
		profile.setHornlength( 0 );
		profile.setGender( 0 );
		profile.setAttractedToGender( 0 );
		profile.setDescription( "" );
		profile.setLastseen( LocalDateTime.now().truncatedTo( ChronoUnit.SECONDS ) );

		if( profileDAO.findByNickname( profile.getNickname() ).isPresent() ) {
			profile.setNickname( faker.lordOfTheRings().character().replaceAll( "\\s+", "" ) );
			log.info( "nickname changed " + profile.getNickname() );
		}

		if( ! unicornDAO.existsByEmail( unicornDTO.getEmail() ) &&
		    ! profileDAO.findByNickname( profile.getNickname() ).isPresent() ) {
			profileDAO.save( profile );
		}

		byte[] bytes = Files.readAllBytes( Paths.get(
				"C:/Users/seisik/atakan/java-tasks/date4u/main/src/main/resources/static/images/unicorns/noPp.jpg" ) );

		photo.setProfile( profile );
		photo.setName( photoService.upload( bytes ) );
		photo.setProfilePhoto( true );
		photo.setCreated( LocalDateTime.now().truncatedTo( ChronoUnit.SECONDS ) );
		photoDAO.save( photo );

		unicorn.setEmail( unicornDTO.getEmail() );
		unicorn.setPassword( "{noop}" + unicornDTO.getPassword() );
		unicorn.setProfile( profile );
		unicornDAO.save( unicorn );
	}
}
