package com.dhkh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.Seat;

public class SeatDAO {
	
	public List<Seat> getAllSeats() {
		List<Seat> seats = new ArrayList<>();
		String sql = "SELECT * FROM SEAT";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Seat seat = new Seat();
					seat.setSeatId(rs.getInt("SEAT_ID"));
					seat.setSeatName(rs.getString("SEAT_NAME"));
					seats.add(seat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seats;
	}
}