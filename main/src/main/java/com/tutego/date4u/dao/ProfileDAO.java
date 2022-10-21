package com.tutego.date4u.dao;

import com.tutego.date4u.dto.ProfileDTO;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.entity.Unicorn;
import com.tutego.date4u.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileDAO {

	private final ProfileRepository profileRepository;

	public ProfileDAO( ProfileRepository profileRepository ) {
		this.profileRepository = profileRepository;
	}

	public List<Profile> findAll() {
		return profileRepository.findAll();
	}

	public Optional<Profile> findById( Long id ) {
		Optional<Profile> profile = profileRepository.findById( id );

		if( profile.isPresent() ) {
			return profile;
		}
		return null;
	}

	public Optional<Profile> findByNickname( String nickname ) {
		return profileRepository.findByNickname( nickname );
	}

	public Profile findByUnicorn( Unicorn unicorn ) {
		return profileRepository.findByUnicorn( unicorn );
	}

	public Optional<Profile> findProfileByNickname( String nickname ) {
		return profileRepository.findByNickname( nickname );
	}

	public Boolean existByNickname( String nickname ) {
		return profileRepository.existsByNickname( nickname );
	}

	public void save( Profile profile ) {
		profileRepository.save( profile );
	}

	public void update( ProfileDTO profile ) {
		Optional<Profile> originalProfile = findById( profile.getId() );

		Profile updatedProfile = originalProfile.get();

		updatedProfile.setNickname( profile.getNickname() );
		updatedProfile.setBirthdate( profile.getBirthdate() );
		updatedProfile.setHornlength( profile.getHornlength() );
		updatedProfile.setGender( profile.getGender() );
		updatedProfile.setAttractedToGender( profile.getAttractedToGender() );
		updatedProfile.setDescription( profile.getDescription() );
		updatedProfile.setLastseen( LocalDateTime.now().truncatedTo( ChronoUnit.SECONDS ) );

		profileRepository.save( updatedProfile );
	}

	public List<Profile> search( LocalDate minAge, LocalDate maxAge, short minHorn, short maxHorn, byte gender ) {
		return profileRepository.search( minAge, maxAge, minHorn, maxHorn, gender );
	}

	public long count() {
		return profileRepository.count();
	}
}
