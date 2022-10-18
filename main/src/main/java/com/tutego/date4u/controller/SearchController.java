package com.tutego.date4u.controller;

import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.util.AgeCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

	Logger log = LoggerFactory.getLogger( SearchController.class );
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

		if( principal != null ) {
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
				model.addAttribute( "userId",
						unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
			    AgeCheckUtil.isOlderThan18(
					    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
				model.addAttribute( "noProfileSet", true );
			}
		}
		return "search";
	}

	@PostMapping( "/search" )
	public String searching( Model model, Principal principal, int minAge, int maxAge, byte gender, short minHorn,
	                         short maxHorn ) {

		if( principal != null ) {
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
				model.addAttribute( "userId",
						unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
			    AgeCheckUtil.isOlderThan18(
					    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
				model.addAttribute( "noProfileSet", true );
			}
		}

		LocalDate currentDate = LocalDate.now();

		Iterable<Profile> profiles = profileDAO.findProfileByGenderAndHornlength( gender, minHorn, maxHorn );
		List<Profile> profileListWithMatchingAge = new ArrayList<>();

		for( Profile profile : profiles ) {
			if( Period.between( profile.getBirthdate(), currentDate ).getYears() >= minAge &&
			    Period.between( profile.getBirthdate(), currentDate ).getYears() <= maxAge ) {
				profileListWithMatchingAge.add( profile );
			}
		}

		List<Photo> profilePhotos =
				profileListWithMatchingAge.stream().map( profile -> photoDAO.findByProfilePhoto( profile ) ).toList();

		model.addAttribute( "profilePhotos", profilePhotos );
		model.addAttribute( "resultList", profileListWithMatchingAge );

		return "search";
	}

}
