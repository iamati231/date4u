package com.tutego.date4u.service;

import com.tutego.date4u.dao.ProfileDAO;
import com.tutego.date4u.entity.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

	private final ProfileDAO profileDAO;

	public SearchService( ProfileDAO profileDAO ) {
		this.profileDAO = profileDAO;
	}

	public List<Profile> getMatches( int minAge, int maxAge, short minHorn, short maxHorn, byte gender ) {
		LocalDate currentDate = LocalDate.now();

		return profileDAO.search( currentDate.minusYears( maxAge ).minusDays( 1 ), currentDate.minusYears( minAge ),
				minHorn, maxHorn, gender );
	}

}
