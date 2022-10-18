package com.tutego.date4u.controller;

import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.dto.UnicornDTO;
import com.tutego.date4u.service.AuthService;
import com.tutego.date4u.util.EmailRegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

	Logger log = LoggerFactory.getLogger( AuthController.class );
	private final AuthService authService;
	private final UnicornDAO unicornDAO;

	public AuthController( AuthService authService, UnicornDAO unicornDAO ) {
		this.authService = authService;
		this.unicornDAO = unicornDAO;
	}

	@GetMapping( "/login" )
	public String login( Model model, Principal principal ) {
		if( principal != null ) {
			model.addAttribute( "noUser", true );
		}
		return "login";
	}

	@GetMapping( value = "/register" )
	public String register( Model model, Principal principal ) {
		if( principal != null ) {
			model.addAttribute( "noUser", true );
		}
		return "register";
	}

	@PostMapping( "/register" )
	public String register( Model model, UnicornDTO unicornDTO ) {
		try {
			if( unicornDAO.existsByEmail( unicornDTO.getEmail() ) ) {
				model.addAttribute( "existsEmail", true );
			}
			if( unicornDTO.getEmail().isEmpty() && unicornDTO.getPassword().isEmpty() ) {
				model.addAttribute( "blankEmail", true );
				model.addAttribute( "blankPassword", true );
				return "register";
			}
			if( unicornDTO.getEmail().isEmpty() ) {
				model.addAttribute( "blankEmail", true );
				return "register";
			}
			if( ! EmailRegexUtil.isValid( unicornDTO.getEmail() ) ) {
				model.addAttribute( "notEmail", true );
				return "register";
			}
			if( unicornDTO.getPassword().isEmpty() ) {
				model.addAttribute( "blankPassword", true );
				return "register";
			}
			if( unicornDTO.getPassword().length() < 8 ) {
				model.addAttribute( "lengthPassword", true );
				return "register";
			}
			authService.register( unicornDTO );
		} catch( Exception e ) {
			return "register";
		}
		return "redirect:/login";
	}

}
