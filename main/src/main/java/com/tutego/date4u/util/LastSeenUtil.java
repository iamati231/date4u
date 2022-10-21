package com.tutego.date4u.util;

import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.dao.UnicornDAO;
import com.tutego.date4u.entity.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LastSeenUtil {

	private final static Logger log = LoggerFactory.getLogger( LastSeenUtil.class );

	public static void lastseen( String principal, ProfileDAO profileDAO, UnicornDAO unicornDAO ) {
		Profile profile = profileDAO.findByUnicorn( unicornDAO.findUnicornByEmail( principal ).get() );
		profile.setLastseen( LocalDateTime.now().truncatedTo( ChronoUnit.SECONDS ) );
		profileDAO.save( profile );

		log.info( principal );
	}
}
