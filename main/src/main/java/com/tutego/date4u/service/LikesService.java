package com.tutego.date4u.service;

import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.entity.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LikesService {

	private final ProfileDAO profileDAO;

	public LikesService( ProfileDAO profileDAO ) { this.profileDAO = profileDAO; }

	public void likeProfile( Profile profile, Long profileId ) {
		Optional<Profile> maybeProfile = profileDAO.findById( profileId );

		Set<Profile> like = profile.getProfilesThatILike();
		like.add( maybeProfile.get() );

		profile.setProfilesThatILike( like );
		profileDAO.save( profile );
	}

	public void unlikeProfile( Profile profile, Long profileId ) {
		Optional<Profile> maybeProfile = profileDAO.findById( profileId );

		Set<Profile> like = profile.getProfilesThatILike();
		like.remove( maybeProfile.get() );

		profile.setProfilesThatILike( like );
		profileDAO.save( profile );
	}
}
