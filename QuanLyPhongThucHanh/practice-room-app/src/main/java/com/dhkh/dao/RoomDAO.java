package com.dhkh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.Room;

public class RoomDAO {
	public Room getRoom() {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = 'Lab - Doanh nghiệp' AND START_DATE <= SYSDATE AND END_DATE >= SYSDATE";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOM_ID"));
					room.setRoomName(rs.getString("ROOM_NAME"));
					room.setStatus(rs.getString("STATUS"));
					room.setStartDate(rs.getDate("START_DATE"));
					room.setEndDate(rs.getDate("END_DATE"));
					room.setStartPeriod(rs.getString("START_PERIOD"));
					room.setEndPeriod(rs.getString("END_PERIOD"));
					room.setReason(rs.getString("REASON"));
					return room;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Room> getClosingSchedule() {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = 'Lab - Doanh nghiệp' AND STATUS = 'Đóng cửa'";
		List<Room> rooms = new ArrayList<>();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOM_ID"));
					room.setRoomName(rs.getString("ROOM_NAME"));
					room.setStatus(rs.getString("STATUS"));
					room.setStartDate(rs.getDate("START_DATE"));
					room.setEndDate(rs.getDate("END_DATE"));
					room.setStartPeriod(rs.getString("START_PERIOD"));
					room.setEndPeriod(rs.getString("END_PERIOD"));
					room.setReason(rs.getString("REASON"));
					rooms.add(room);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}
	
	public boolean updateRoom(Room room) {
	    String sql = "UPDATE ROOM SET ROOM_NAME = ?, STATUS = ?, START_DATE = ?, END_DATE = ? WHERE ROOM_ID = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, room.getRoomName());
			stmt.setString(2, room.getStatus());
			stmt.setDate(3, room.getStartDate());
			stmt.setDate(4, room.getEndDate());
			stmt.setInt(5, room.getRoomId());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean newRoomSchedule(Room room) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append(" INSERT INTO ROOM(ROOM_ID, ROOM_NAME, STATUS, START_DATE, END_DATE, START_PERIOD, END_PERIOD, REASON) ")
			.append(" VALUES (ROOM_SEQ.NEXTVAL, 'Lab - Doanh nghiệp', ?, ?, ?, ?, ?, ?) ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, room.getStatus());
			stmt.setDate(2, room.getStartDate());
			stmt.setDate(3, room.getEndDate());
			stmt.setString(4, room.getStartPeriod());
			stmt.setString(5, room.getEndPeriod());
			stmt.setString(6, room.getReason());
			
			int rowInserted = stmt.executeUpdate();
			return rowInserted > 0;
		} catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean isClosedSchedule(Date usingDate, String period) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append(" SELECT  ")
			.append("     COUNT(*) AS COUNT ")
			.append(" FROM ROOM ")
			.append(" WHERE ? BETWEEN START_DATE AND END_DATE  ")
			.append(" AND ( ")
			.append("     (? = START_DATE AND ( ")
			.append("         (? = START_PERIOD) ")
			.append("         OR (? = 'Chiều') ")
			.append("     )) ")
			.append("     OR (? BETWEEN START_DATE + 1 AND END_DATE - 1) ")
			.append("     OR (? = END_DATE AND ( ")
			.append("         (? = END_PERIOD) ")
			.append("         OR (? = 'Sáng') ")
			.append("     )) ")
			.append(" ) ");
		String sql = sqlBuilder.toString();
		//String sql = "SELECT COUNT(*) AS COUNT FROM ROOM WHERE ? BETWEEN START_DATE AND END_DATE";
		boolean exists = false;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, usingDate);
			stmt.setDate(2, usingDate);
			stmt.setString(3, period);
			stmt.setString(4, period);
			stmt.setDate(5, usingDate);
			stmt.setDate(6, usingDate);
			stmt.setString(7, period);
			stmt.setString(8, period);
			
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
	
	public boolean isExistClosedSchedule(Date startDate, Date endDate) {
		String sql = "SELECT COUNT(*) AS COUNT FROM ROOM WHERE (? BETWEEN START_DATE AND END_DATE) OR (? BETWEEN START_DATE AND END_DATE)";
		boolean exists = false;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, startDate);
			stmt.setDate(2, endDate);
			
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
	
	public boolean deleteRoomSchedule(int roomId) {
	    String sql = "DELETE FROM ROOM WHERE ROOM_ID = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setInt(1, roomId);
	        
	        int rowsdeleted = stmt.executeUpdate();
	        return rowsdeleted > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}