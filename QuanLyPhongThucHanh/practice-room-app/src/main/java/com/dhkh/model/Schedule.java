package com.dhkh.model;

import java.sql.Date;

public class Schedule {
	private int scheduleId;
	private Date usingDate;
	private String usingDateStr;
	private String period;
	private int userId;
	private String username;
	private String email;
	private int seatId;
	private String seatName;
	private int confirmed;
	
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getUsingDate() {
		return usingDate;
	}

	public void setUsingDate(Date usingDate) {
		this.usingDate = usingDate;
	}

	public String getUsingDateStr() {
		return usingDateStr;
	}

	public void setUsingDateStr(String usingDateStr) {
		this.usingDateStr = usingDateStr;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	
}