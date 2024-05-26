package com.dhkh.model;

import java.sql.Date;

public class SubSchedule {
	private int subScheduleId;
	private Date assignDate;
	private String assignDateStr;
	private String period;
	private int userId;
	private String username;
	
	public SubSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSubScheduleId() {
		return subScheduleId;
	}

	public void setSubScheduleId(int subScheduleId) {
		this.subScheduleId = subScheduleId;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
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

	public String getAssignDateStr() {
		return assignDateStr;
	}

	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}
	
}