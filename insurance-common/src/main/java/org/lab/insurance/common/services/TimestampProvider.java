package org.lab.insurance.common.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
public class TimestampProvider {

	@Getter
	@Setter
	private Date fakeDate;

	// TODO use server date
	public Date getCurrentDate() {
		return fakeDate != null ? fakeDate : Calendar.getInstance().getTime();
	}

	public Date getRealDate() {
		return Calendar.getInstance().getTime();
	}

}