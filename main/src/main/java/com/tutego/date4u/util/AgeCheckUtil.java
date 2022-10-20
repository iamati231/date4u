package com.tutego.date4u.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;

public class AgeCheckUtil {

	private static final Logger log = LoggerFactory.getLogger( AgeCheckUtil.class );

	public static boolean isOlderThan18( LocalDate date ) {
		LocalDate today = LocalDate.now();
		LocalDate birthday = date;

		Period p = Period.between( birthday, today );
		log.info( String.valueOf( p.getYears() ) );
		return p.getYears() < 18;
	}

}
