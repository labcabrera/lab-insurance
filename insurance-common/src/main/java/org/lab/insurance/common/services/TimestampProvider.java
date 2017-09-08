package org.lab.insurance.common.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimestampProvider {

	// TODO use server date
	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

}