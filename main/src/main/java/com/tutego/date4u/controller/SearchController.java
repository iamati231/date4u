package com.tutego.date4u.controller;

import com.tutego.date4u.dao.PhotoDAO;
import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.dto.SearchDTO;
import com.tutego.date4u.entity.Photo;
import com.tutego.date4u.entity.Profile;
import com.tutego.date4u.util.AgeCheckUtil;
import com.tutego.date4u.util.LastSeenUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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

	@GetMapping( { "/search", "/search/{pageNo}" } )
	public String search( Model model, Principal principal, @PathVariable( "pageNo" ) Optional<Integer> pageNo,
	                      HttpSession session ) {

		Optional<Object> filterOpt = Optional.ofNullable( session.getAttribute( "filter" ) );

		SearchDTO searchDTO;
		if( filterOpt.isEmpty() ) {
			searchDTO = new SearchDTO();
		} else {
			searchDTO = ( SearchDTO ) filterOpt.get();
		}

		int pageNumber;
		if( pageNo.isEmpty() ) {
			pageNumber = 1;
		} else {
			pageNumber = pageNo.get();
		}

		final int pageSize = 4;
		Pageable page = PageRequest.of( pageNumber - 1, pageSize, Sort.by( "lastseen" ).descending() );
		Page<Profile> profiles = profileDAO.search( searchDTO, page );

		List<Photo> profilePhotos = profiles.stream().map( profile -> photoDAO.findByProfilePhoto( profile ) ).toList();

		List<Long> days = profiles.stream()
				.map( profile -> ChronoUnit.DAYS.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		List<Long> hours = profiles.stream()
				.map( profile -> ChronoUnit.HOURS.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		List<Long> minutes = profiles.stream()
				.map( profile -> ChronoUnit.MINUTES.between( profile.getLastseen(), LocalDateTime.now() ) ).toList();

		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
			model.addAttribute( "userId",
					unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
		}
		if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
		    AgeCheckUtil.isOlderThan18(
				    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
			model.addAttribute( "noProfileSet", true );
		}

		model.addAttribute( "profiles", profileDAO.findAll() );
		model.addAttribute( "resultList", profiles );
		model.addAttribute( "numberOfResults", profiles.stream().toList().size() );
		model.addAttribute( "profilePhotos", profilePhotos );
		model.addAttribute( "lastseenDays", days );
		model.addAttribute( "lastseenHours", hours );
		model.addAttribute( "lastseenMinutes", minutes );
		model.addAttribute( "searchDTO", searchDTO );
		model.addAttribute( "currentPage", pageNumber );
		model.addAttribute( "totalPages", profiles.getTotalPages() );
		model.addAttribute( "totalItems", profiles.getTotalElements() );
		model.addAttribute( "previous", profiles.previousPageable() );

		LastSeenUtil.lastseen( principal.getName(), profileDAO, unicornDAO );

		return "search";
	}

	@PostMapping( value = "/search" )
	public String searching( @ModelAttribute SearchDTO searchDTO, HttpSession session ) {
		session.setAttribute( "filter", searchDTO );

		return "redirect:/search";
	}

}
