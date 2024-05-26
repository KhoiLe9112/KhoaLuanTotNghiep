package com.dhkh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.Schedule;

public class ScheduleDAO {
	public List<Schedule> getAllSchedules(Date usingDate, String period) {
		List<Schedule> schedules = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    SC.*, ")
			.append("    S.SEAT_NAME, ")
			.append("    U.USERNAME, ")
			.append("    U.EMAIL ")
			.append("FROM ")
			.append("    SCHEDULE SC ")
			.append("JOIN ")
			.append("    SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append("JOIN ")
			.append("    USERS U ON SC.USER_ID = U.USER_ID ")
			.append("WHERE ")
			.append("    SC.USING_DATE = ? ")
			.append("AND ")
			.append("    SC.PERIOD = ? ")
			.append("AND ")
			.append("    SC.DELETED = 0 ")
			.append("ORDER BY ")
			.append("    TO_NUMBER(SUBSTR(S.SEAT_NAME, 2)), ")
			.append("    S.SEAT_NAME ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, usingDate);
			stmt.setString(2, period);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Schedule schedule = new Schedule();
					schedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
					schedule.setUsingDate(rs.getDate("USING_DATE"));
					schedule.setPeriod(rs.getString("PERIOD"));
					schedule.setUserId(rs.getInt("USER_ID"));
					schedule.setUsername(rs.getString("USERNAME"));
					schedule.setEmail(rs.getString("EMAIL"));
					schedule.setSeatId(rs.getInt("SEAT_ID"));
					schedule.setSeatName(rs.getString("SEAT_NAME"));
					schedule.setConfirmed(rs.getInt("CONFIRMED"));
					schedules.add(schedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	public List<Schedule> getAllUserSchedules(Date usingDate, String period, int userId) {
		List<Schedule> schedules = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    SC.*, ")
			.append("    S.SEAT_NAME, ")
			.append("    U.USERNAME, ")
			.append("    U.EMAIL ")
			.append("FROM ")
			.append("    SCHEDULE SC ")
			.append("JOIN ")
			.append("    SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append("JOIN ")
			.append("    USERS U ON SC.USER_ID = U.USER_ID ")
			.append("WHERE ")
			.append("    SC.USING_DATE = ? ")
			.append("AND ")
			.append("    SC.PERIOD = ? ")
			.append("AND ")
			.append("    SC.USER_ID = ? ")
			.append("AND ")
			.append("    SC.DELETED = 0 ")
			.append("ORDER BY ")
			.append("    TO_NUMBER(SUBSTR(S.SEAT_NAME, 2)), ")
			.append("    S.SEAT_NAME ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, usingDate);
			stmt.setString(2, period);
			stmt.setInt(3, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Schedule schedule = new Schedule();
					schedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
					schedule.setUsingDate(rs.getDate("USING_DATE"));
					schedule.setPeriod(rs.getString("PERIOD"));
					schedule.setUserId(rs.getInt("USER_ID"));
					schedule.setUsername(rs.getString("USERNAME"));
					schedule.setEmail(rs.getString("EMAIL"));
					schedule.setSeatId(rs.getInt("SEAT_ID"));
					schedule.setSeatName(rs.getString("SEAT_NAME"));
					schedule.setConfirmed(rs.getInt("CONFIRMED"));
					schedules.add(schedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	public Schedule getScheduleById(int scheduleId) {
		Schedule schedule = new Schedule();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    SC.*, ")
			.append("    S.SEAT_NAME, ")
			.append("    U.USERNAME, ")
			.append("    U.EMAIL ")
			.append("FROM ")
			.append("    SCHEDULE SC ")
			.append("JOIN ")
			.append("    SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append("JOIN ")
			.append("    USERS U ON SC.USER_ID = U.USER_ID ")
			.append("WHERE ")
			.append("    SC.SCHEDULE_ID = ? ")
			.append("AND ")
			.append("    SC.DELETED = 0 ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, scheduleId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					schedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
					schedule.setUsingDate(rs.getDate("USING_DATE"));
					schedule.setPeriod(rs.getString("PERIOD"));
					schedule.setUserId(rs.getInt("USER_ID"));
					schedule.setUsername(rs.getString("USERNAME"));
					schedule.setEmail(rs.getString("EMAIL"));
					schedule.setSeatId(rs.getInt("SEAT_ID"));
					schedule.setSeatName(rs.getString("SEAT_NAME"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}
	
	public boolean newSeatSchedule(Schedule schedule) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("INSERT INTO SCHEDULE(SCHEDULE_ID, USING_DATE, PERIOD, USER_ID, SEAT_ID) ")
		    .append("VALUES( ")
		    .append("    SCHEDULE_SEQ.NEXTVAL, ")
		    .append("    ?, ")
		    .append("    ?, ")
		    .append("    ?, ")
		    .append("    ? ")
		    .append("    ) ");
		String sql = sqlBuilder.toString();		
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, schedule.getUsingDate());
			stmt.setString(2, schedule.getPeriod());
			stmt.setInt(3, schedule.getUserId());
			stmt.setInt(4, schedule.getSeatId());
			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isScheduleConflict(Schedule schedule) {
		String sql = "SELECT COUNT(*) AS COUNT FROM SCHEDULE WHERE SEAT_ID = ? AND PERIOD = ? AND USING_DATE = ? AND DELETED = 0";
		boolean conflict = false;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, schedule.getSeatId());
	        stmt.setString(2, schedule.getPeriod());
	        stmt.setDate(3, schedule.getUsingDate());
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	        	if (rs.next()) {
	        		int count = rs.getInt("COUNT");
	        		conflict = (count > 0);
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conflict;
	}
	
	public boolean deleteSeatSchedule(int scheduleId) {
	    //String sql = "DELETE FROM SCHEDULE WHERE SCHEDULE_ID = ?";
	    String sql = "UPDATE SCHEDULE SET DELETED = 1 WHERE SCHEDULE_ID = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, scheduleId);
	        
	        int rowsDeleted = stmt.executeUpdate();
	        return rowsDeleted > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean deleteScheduleByClosed(Date startDate, Date endDate, String startPeriod, String endPeriod) {
	    //String sql = "DELETE FROM SCHEDULE WHERE USING_DATE BETWEEN ? AND ?";
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append(" UPDATE SCHEDULE ")
			.append(" SET DELETED = 1 ")
			.append(" WHERE USING_DATE BETWEEN TO_DATE(?, 'DD-MON-YY') AND TO_DATE(?, 'DD-MON-YY') ")
			.append(" AND ( ")
			.append("     (USING_DATE = TO_DATE(?, 'DD-MON-YY') AND ( ")
			.append("         (PERIOD = ?) ")
			.append("         OR (PERIOD = 'Chiều') ")
			.append("     )) ")
			.append("     OR (USING_DATE BETWEEN TO_DATE(?, 'DD-MON-YY') + 1 AND TO_DATE(?, 'DD-MON-YY') - 1) ")
			.append("     OR (USING_DATE = TO_DATE(?, 'DD-MON-YY') AND ( ")
			.append("         (PERIOD = ?) ")
			.append("         OR (PERIOD = 'Sáng') ")
			.append("     )) ")
			.append(" ) ");
		String sql = sqlBuilder.toString();
	    //String sql = "UPDATE SCHEDULE SET DELETED = 1 WHERE USING_DATE BETWEEN ? AND ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setDate(1, startDate);
	        stmt.setDate(2, endDate);
	        stmt.setDate(3, startDate);
	        stmt.setString(4, startPeriod);
	        stmt.setDate(5, startDate);
	        stmt.setDate(6, endDate);
	        stmt.setDate(7, endDate);
	        stmt.setString(8, endPeriod);
	        
	        int rowsDeleted = stmt.executeUpdate();
	        return rowsDeleted > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<Schedule> getSchedulesByUsingDate(Date startDate, Date endDate) {
		List<Schedule> schedules = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    SC.*, ")
			.append("    S.SEAT_NAME, ")
			.append("    U.USERNAME, ")
			.append("    U.EMAIL ")
			.append("FROM ")
			.append("    SCHEDULE SC ")
			.append("JOIN ")
			.append("    SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append("JOIN ")
			.append("    USERS U ON SC.USER_ID = U.USER_ID ")
			.append("WHERE ")
			.append("    USING_DATE ")
			.append("BETWEEN ")
			.append("    ? ")
			.append("AND ")
			.append("    ? ")
			.append("AND ")
			.append("    SC.DELETED = 0 ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, startDate);
	        stmt.setDate(2, endDate);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Schedule schedule = new Schedule();
					schedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
					schedule.setUsingDate(rs.getDate("USING_DATE"));
					schedule.setPeriod(rs.getString("PERIOD"));
					schedule.setUserId(rs.getInt("USER_ID"));
					schedule.setUsername(rs.getString("USERNAME"));
					schedule.setEmail(rs.getString("EMAIL"));
					schedule.setSeatId(rs.getInt("SEAT_ID"));
					schedule.setSeatName(rs.getString("SEAT_NAME"));
					schedules.add(schedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	public List<Schedule> getDeletedSchedules(Date startDate, Date endDate, String startPeriod, String endPeriod) {
		List<Schedule> schedules = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append(" SELECT  ")
			.append("     SC.*, ")
			.append("     S.SEAT_NAME, ")
			.append("     U.USERNAME, ")
			.append("     U.EMAIL ")
			.append(" FROM SCHEDULE SC ")
			.append(" JOIN SEAT S ON SC.SEAT_ID = S.SEAT_ID ")
			.append(" JOIN USERS U ON SC.USER_ID = U.USER_ID ")
			.append(" WHERE SC.USING_DATE BETWEEN TO_DATE(?, 'DD-MON-YY') AND TO_DATE(?, 'DD-MON-YY') ")
			.append(" AND ( ")
			.append("     (SC.USING_DATE = TO_DATE(?, 'DD-MON-YY') AND ( ")
			.append("         (SC.PERIOD = ?) ")
			.append("         OR (SC.PERIOD = 'Chiều') ")
			.append("     )) ")
			.append("     OR (SC.USING_DATE BETWEEN TO_DATE(?, 'DD-MON-YY') + 1 AND TO_DATE(?, 'DD-MON-YY') - 1) ")
			.append("     OR (SC.USING_DATE = TO_DATE(?, 'DD-MON-YY') AND ( ")
			.append("         (SC.PERIOD = ?) ")
			.append("         OR (SC.PERIOD = 'Sáng') ")
			.append("     )) ")
			.append(" ) ")
			.append(" ORDER BY SC.USING_DATE ASC,  SC.PERIOD DESC ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, startDate);
	        stmt.setDate(2, endDate);
	        stmt.setDate(3, startDate);
	        stmt.setString(4, startPeriod);
	        stmt.setDate(5, startDate);
	        stmt.setDate(6, endDate);
	        stmt.setDate(7, endDate);
	        stmt.setString(8, endPeriod);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Schedule schedule = new Schedule();
					schedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
					schedule.setUsingDate(rs.getDate("USING_DATE"));
					schedule.setPeriod(rs.getString("PERIOD"));
					schedule.setUserId(rs.getInt("USER_ID"));
					schedule.setUsername(rs.getString("USERNAME"));
					schedule.setEmail(rs.getString("EMAIL"));
					schedule.setSeatId(rs.getInt("SEAT_ID"));
					schedule.setSeatName(rs.getString("SEAT_NAME"));
					schedules.add(schedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	public boolean confirmStudent(int userId, String period) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("UPDATE SCHEDULE ")
			.append("SET CONFIRMED = 1 ")
			.append("WHERE USER_ID = ? ")
			.append("AND USING_DATE = TRUNC(SYSDATE) ")
			.append("AND PERIOD = ? ")
			.append("AND DELETED = 0 ");
		String sql = sqlBuilder.toString();
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setString(2, period);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
}