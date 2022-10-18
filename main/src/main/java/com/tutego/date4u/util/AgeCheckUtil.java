package com.tutego.date4u.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCheckUtil {

	public static boolean isOlderThan18( LocalDate date ) {
		LocalDate today = LocalDate.now();
		LocalDate birthday = date;

		Period p = Period.between( birthday, today );
		return p.getYears() < 18;
	}

}
