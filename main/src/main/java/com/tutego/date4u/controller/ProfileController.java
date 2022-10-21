package com.tutego.date4u.controller;

import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.dto.ProfileDTO;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.service.LikesService;
import com.tutego.date4u.service.PhotoService;
import com.tutego.date4u.util.AgeCheckUtil;
import com.tutego.date4u.util.LastSeenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

	private final Logger log = LoggerFactory.getLogger( ProfileController.class );
	private final ProfileDAO profileDAO;
	private final UnicornDAO unicornDAO;
	private final PhotoDAO photoDAO;
	private final PhotoService photoService;
	private final LikesService likesService;

	public ProfileController( ProfileDAO profileDAO, UnicornDAO unicornDAO, PhotoDAO photoDAO,
	                          PhotoService photoService, LikesService likesService ) {
		this.profileDAO = profileDAO;
		this.unicornDAO = unicornDAO;
		this.photoDAO = photoDAO;
		this.photoService = photoService;
		this.likesService = likesService;
	}

	@GetMapping( value = "/profile/{id}" )
	public String profile( Model model, Principal principal, @PathVariable long id ) {
		Optional<Profile> maybeProfile = profileDAO.findById( id );

		if( ! maybeProfile.isPresent() ) {
			return "redirect:/index";
		}

		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
			model.addAttribute( "userId",
					unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
		}
		if( id == unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() ) {
			model.addAttribute( "isProfile", true );
			if( AgeCheckUtil.isOlderThan18(
					unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
				model.addAttribute( "notOlderThan18", true );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription()
					.equals( "" ) ) {
				model.addAttribute( "descriptionIsEmpty", true );
			}
		}
		LastSeenUtil.lastseen( principal.getName(), profileDAO, unicornDAO );

		Profile profile = maybeProfile.get();
		model.addAttribute( "profile",
				new ProfileDTO( profile.getId(), profile.getNickname(), profile.getBirthdate(), profile.getHornlength(),
						profile.getGender(), profile.getAttractedToGender(), profile.getDescription(),
						profile.getLastseen() ) );

		model.addAttribute( "email", unicornDAO.findByProfile( profile ).getEmail() );

		List<String> allPhotos = new ArrayList<>();

		if( photoDAO.findByProfile( profile ) != null ) {
			photoDAO.findByProfile( profile ).forEach( photo -> allPhotos.add( photo.getName() ) );
			if( photoDAO.findByProfilePhoto( profile ) != null ) {
				model.addAttribute( "pp",
						photoDAO.findByProfilePhoto( profile ).getProfile().getProfilePhoto().getName() );
			} else {
				model.addAttribute( "noPp", true );
			}
			model.addAttribute( "allPhotos", allPhotos );
			model.addAttribute( "numberOfPhotos", allPhotos.size() );
		}
		if( allPhotos.isEmpty() ) {
			model.addAttribute( "noPhoto", true );
		}

		List<Profile> likees = profile.getProfilesThatILike().stream().toList();
		List<Profile> likers = profile.getProfilesThatLikeMe().stream().toList();

		model.addAttribute( "likees", likees );
		model.addAttribute( "likers", likers );

		if( likers.contains( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() ) ) {
			model.addAttribute( "isLiked", true );
		}

		return "profile";
	}

	@PostMapping( value = "/save" )
	public String save( @ModelAttribute( "profile" ) ProfileDTO profile ) {
		try {
			profileDAO.update( profile );
		} catch( Exception e ) {
			return "redirect:/profile/" + profile.getId();
		}
		return "redirect:/profile/" + profile.getId();
	}

	@PostMapping( value = "/changePp" )
	public String changePp( Principal principal, @ModelAttribute( "photoName" ) String photoName ) {
		Profile profile = unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile();

		photoDAO.update( profile, photoName );

		return "redirect:/profile/" + unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId();
	}

	@PostMapping( value = "/uploadPhoto" )
	public String uploadPhoto( Principal principal, @RequestParam( "image" ) MultipartFile multipartFile )
			throws IOException {
		Profile profile = unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile();

		photoService.uploadPhoto( profile.getId(), multipartFile );

		return "redirect:/profile/" + unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId();
	}

	@PostMapping( value = "/likeProfile" )
	public String likeProfile( Principal principal, Long profileId ) {
		Profile profile = unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile();

		likesService.likeProfile( profile, profileId );


		return "redirect:/profile/" + profileId;
	}

	@PostMapping( value = "/unlikeProfile" )
	public String unlikeProfile( Principal principal, Long profileId ) {
		Profile profile = unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile();

		likesService.unlikeProfile( profile, profileId );

		return "redirect:/profile/" + profileId;
	}

}
