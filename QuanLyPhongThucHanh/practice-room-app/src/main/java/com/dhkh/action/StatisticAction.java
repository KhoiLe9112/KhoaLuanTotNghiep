package com.dhkh.action;

import java.util.List;

import com.dhkh.dao.StatisticDAO;
import com.dhkh.model.DateStatistic;
import com.dhkh.model.PeriodStatistic;
import com.dhkh.model.SeatStatistic;
import com.dhkh.model.UserStatistic;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private int totalReservations;
	private List<PeriodStatistic> periodStatistics;
	private List<DateStatistic> dateStatistics;
	private List<UserStatistic> userStatistics;
	private List<SeatStatistic> seatStatistics;

	public String execute() {
		StatisticDAO statisticDAO = new StatisticDAO();
		totalReservations = statisticDAO.getTotalOfSeatReservations();
		periodStatistics = statisticDAO.getPeriodBookingStatistics();
		dateStatistics = statisticDAO.getWeeklyBookingStatistics();
		userStatistics = statisticDAO.getTopBookingByUserStatistics();
		seatStatistics = statisticDAO.getTopBookingSeatsStatistics();
		
		return SUCCESS;
	}

	public int getTotalReservations() {
		return totalReservations;
	}

	public void setTotalReservations(int totalReservations) {
		this.totalReservations = totalReservations;
	}

	public List<PeriodStatistic> getPeriodStatistics() {
		return periodStatistics;
	}

	public void setPeriodStatistics(List<PeriodStatistic> periodStatistics) {
		this.periodStatistics = periodStatistics;
	}

	public List<DateStatistic> getDateStatistics() {
		return dateStatistics;
	}

	public void setDateStatistics(List<DateStatistic> dateStatistics) {
		this.dateStatistics = dateStatistics;
	}

	public List<UserStatistic> getUserStatistics() {
		return userStatistics;
	}

	public void setUserStatistics(List<UserStatistic> userStatistics) {
		this.userStatistics = userStatistics;
	}

	public List<SeatStatistic> getSeatStatistics() {
		return seatStatistics;
	}

	public void setSeatStatistics(List<SeatStatistic> seatStatistics) {
		this.seatStatistics = seatStatistics;
	}

}