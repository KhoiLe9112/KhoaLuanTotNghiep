package com.dhkh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.DateStatistic;
import com.dhkh.model.PeriodStatistic;
import com.dhkh.model.SeatStatistic;
import com.dhkh.model.UserStatistic;

public class StatisticDAO {
	/**
	 * Function to statistics on the total number of seat reservations
	 * @return
	 */
	public int getTotalOfSeatReservations() {
		int total = 0;
		String sql = "SELECT COUNT(*) AS total_bookings FROM SCHEDULE";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					total = rs.getInt("TOTAL_BOOKINGS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * Function to statistics on number of seat reservations in the last 7 days
	 * @return
	 */
	public List<DateStatistic> getWeeklyBookingStatistics() {
        List<DateStatistic> dateStatistics = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT USING_DATE, COUNT(*) AS total_bookings ")
			.append("FROM SCHEDULE ")
			.append("WHERE USING_DATE >= TRUNC(SYSDATE) - 7 ")
			.append("GROUP BY USING_DATE ")
			.append("ORDER BY USING_DATE ");
		String sql = sqlBuilder.toString();
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DateStatistic dateStatistic = new DateStatistic();
                dateStatistic.setUsingDate(rs.getDate("USING_DATE"));
                dateStatistic.setTotal(rs.getInt("TOTAL_BOOKINGS"));
                dateStatistics.add(dateStatistic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStatistics;
    }
	
	/**
	 * Function to statistics on number of seat reservations by period
	 * @return
	 */
	public List<PeriodStatistic> getPeriodBookingStatistics() {
        List<PeriodStatistic> periodStatistics = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT PERIOD, COUNT(*) AS total_bookings ")
			.append("FROM SCHEDULE ")
			.append("GROUP BY PERIOD ");
		String sql = sqlBuilder.toString();
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PeriodStatistic periodStatistic = new PeriodStatistic();
                periodStatistic.setPeriod(rs.getString("PERIOD").toUpperCase());
                periodStatistic.setTotal(rs.getInt("TOTAL_BOOKINGS"));
                periodStatistics.add(periodStatistic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return periodStatistics;
    }
	
	/**
	 * Function to statistics top booking by user
	 * @return
	 */
	public List<UserStatistic> getTopBookingByUserStatistics() {
        List<UserStatistic> userStatistics = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT U.USERNAME, COUNT(S.SCHEDULE_ID) AS total_bookings ")
			.append("FROM SCHEDULE S ")
			.append("JOIN USERS U ON S.USER_ID = U.USER_ID ")
			.append("GROUP BY U.USERNAME ")
			.append("ORDER BY total_bookings DESC ")
			.append("FETCH FIRST 5 ROWS ONLY ");
		String sql = sqlBuilder.toString();
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
            	UserStatistic userStatistic = new UserStatistic();
            	userStatistic.setUsername(rs.getString("USERNAME"));
            	userStatistic.setTotal(rs.getInt("TOTAL_BOOKINGS"));
            	userStatistics.add(userStatistic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userStatistics;
    }
	
	/**
	 * Function to statistics top booking seats
	 * @return
	 */
	public List<SeatStatistic> getTopBookingSeatsStatistics() {
        List<SeatStatistic> seatStatistics = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT S.SEAT_NAME, COUNT(SC.SCHEDULE_ID) AS total_bookings ")
			.append("FROM SCHEDULE SC ")
			.append("JOIN SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append("GROUP BY S.SEAT_NAME ")
			.append("ORDER BY total_bookings DESC ")
			.append("FETCH FIRST 5 ROWS ONLY ");
		String sql = sqlBuilder.toString();
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
            	SeatStatistic seatStatistic = new SeatStatistic();
            	seatStatistic.setSeatName(rs.getString("SEAT_NAME"));
            	seatStatistic.setTotal(rs.getInt("TOTAL_BOOKINGS"));
            	seatStatistics.add(seatStatistic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatStatistics;
    }
}