package com.tutego.date4u.controller;

import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.service.AuthService;
import com.tutego.date4u.util.AgeCheckUtil;
import com.tutego.date4u.util.LastSeenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Controller
public class Date4uWebController {

	private final Logger log = LoggerFactory.getLogger( getClass() );
	private final ProfileDAO profileDAO;
	private final UnicornDAO unicornDAO;
	private final AuthService authService;

	public Date4uWebController( ProfileDAO profileDAO, UnicornDAO unicornDAO, AuthService authService ) {
		this.profileDAO = profileDAO;
		this.unicornDAO = unicornDAO;
		this.authService = authService;
	}

	@GetMapping( value = "/*" )
	public String index( Model model, Principal principal ) {
		model.addAttribute( "totalProfiles", profileDAO.count() );

		if( principal != null ) {
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
				model.addAttribute( "userId",
						unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getId() );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
				model.addAttribute( "nickname",
						unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getNickname() );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile() != null ) {
				model.addAttribute( "lastseen",
						unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getLastseen().format(
								DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
										.withLocale( new Locale( "de" ) ) ) );
			}
			if( unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getDescription().equals( "" ) ||
			    AgeCheckUtil.isOlderThan18(
					    unicornDAO.findUnicornByEmail( principal.getName() ).get().getProfile().getBirthdate() ) ) {
				model.addAttribute( "noProfileSet", true );
			}
			LastSeenUtil.lastseen( principal.getName(), profileDAO, unicornDAO );
		} else {
			model.addAttribute( "noUser", true );
		}
		return "index";
	}

}