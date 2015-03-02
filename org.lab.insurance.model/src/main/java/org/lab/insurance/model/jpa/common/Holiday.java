package org.lab.insurance.model.jpa.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "C_HOLIDAY")
@NamedQueries({ @NamedQuery(name = "Holiday.countByDate", query = "select count(e) from Holiday e where e.holidayDate = :value"),
		@NamedQuery(name = "Holiday.countByDateInCalendars", query = "select count(e) from Holiday e where e.holidayDate = :value and e.calendar in :calendars") })
public class Holiday {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "HOLIDAY_DATE")
	@Temporal(TemporalType.DATE)
	private Date holidayDate;

	@Column(name = "DATE")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "CALENDAR_ID", nullable = false)
	private HolidayCalendar calendar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HolidayCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(HolidayCalendar calendar) {
		this.calendar = calendar;
	}
}
