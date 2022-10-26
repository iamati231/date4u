package com.tutego.date4u.controller;

import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.dto.SearchDTO;
import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.util.AgeCheckUtil;
import com.tutego.date4u.util.LastSeenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class SearchController {

	private final Logger log = LoggerFactory.getLogger( SearchController.class );
	private final ProfileDAO profileDAO;
	private final UnicornDAO unicornDAO;
	private final PhotoDAO photoDAO;

	public SearchController( ProfileDAO profileDAO, UnicornDAO unicornDAO, PhotoDAO photoDAO ) {
		this.profileDAO = profileDAO;
		this.unicornDAO = unicornDAO;
		this.photoDAO = photoDAO;
	}

	@GetMapping( "/search" )
	public String search( Model model, Principal principal ) {
		model.addAttribute( "profiles", profileDAO.findAll() );

		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
			model.addAttribute( "userId",
					unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
		}
		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
		    AgeCheckUtil.isOlderThan18(
				    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
			model.addAttribute( "noProfileSet", true );
		}
		LastSeenUtil.lastseen( principal.getName(), profileDAO, unicornDAO );
		return "search";
	}

	@PostMapping( "/search" )
	public String searching( Model model, Principal principal, SearchDTO searchDTO ) {

		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
			model.addAttribute( "userId",
					unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
		}
		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
		    AgeCheckUtil.isOlderThan18(
				    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
			model.addAttribute( "noProfileSet", true );
		}

		List<Profile> profiles = profileDAO.search( searchDTO );

		List<Photo> profilePhotos = profiles.stream().map( profile -> photoDAO.findByProfilePhoto( profile ) ).toList();

		List<Long> days = profiles.stream()
				.map( profile -> ChronoUnit.DAYS.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		List<Long> hours = profiles.stream()
				.map( profile -> ChronoUnit.HOURS.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		List<Long> minutes = profiles.stream()
				.map( profile -> ChronoUnit.MINUTES.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		model.addAttribute( "resultList", profiles );
		model.addAttribute( "numberOfResults", profiles.size() );
		model.addAttribute( "profilePhotos", profilePhotos );
		model.addAttribute( "lastseenDays", days );
		model.addAttribute( "lastseenHours", hours );
		model.addAttribute( "lastseenMinutes", minutes );

		return "search";
	}

}
