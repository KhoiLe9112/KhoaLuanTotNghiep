package com.dhkh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.SubSchedule;

public class SubScheduleDAO {
	public List<SubSchedule> getAllSubSchedule() {
		List<SubSchedule> subSchedules = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    ss.*, ")
			.append("    u.username ")
			.append("FROM ")
			.append("    sub_schedule ss ")
			.append("JOIN ")
			.append("    users u ON ss.user_id = u.user_id ")
			.append("WHERE ")
			.append("    assign_date >= ( sysdate - 1 ) ")
			.append("ORDER BY ")
			.append("    assign_date ASC ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					SubSchedule subSchedule = new SubSchedule();
					subSchedule.setSubScheduleId(rs.getInt("SUB_SCHEDULE_ID"));
					subSchedule.setAssignDate(rs.getDate("ASSIGN_DATE"));
					subSchedule.setPeriod(rs.getString("PERIOD"));
					subSchedule.setUserId(rs.getInt("USER_ID"));
					subSchedule.setUsername(rs.getString("USERNAME"));
					subSchedules.add(subSchedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subSchedules;
	}
	
	public boolean newSubSchedule(SubSchedule subSchedule) {
		String sql = "INSERT INTO SUB_SCHEDULE(SUB_SCHEDULE_ID, ASSIGN_DATE, USER_ID, PERIOD) VALUES (SUB_SCHEDULE_SEQ.NEXTVAL, ?, ?, ?)";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, subSchedule.getAssignDate());
			stmt.setInt(2, subSchedule.getUserId());
			stmt.setString(3, subSchedule.getPeriod());
			
			int rowInserted = stmt.executeUpdate();
			return rowInserted > 0;
		} catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean isAssignDateExists(Date assignDate, String period) {
		String sql = "SELECT COUNT(*) AS COUNT FROM SUB_SCHEDULE WHERE ASSIGN_DATE = ? AND PERIOD = ?";
		boolean exists = false;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, assignDate);
			stmt.setString(2, period);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt("COUNT");
					return exists = count > 0;
				}
			}
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		return exists;
	}
}