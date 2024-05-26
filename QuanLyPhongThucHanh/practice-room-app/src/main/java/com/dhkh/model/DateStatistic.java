package com.dhkh.model;

import java.sql.Date;

public class DateStatistic {
    private Date usingDate;
    private int total;
    
	public DateStatistic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getUsingDate() {
		return usingDate;
	}
	public void setUsingDate(Date usingDate) {
		this.usingDate = usingDate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}