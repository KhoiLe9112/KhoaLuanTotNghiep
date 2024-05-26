package com.dhkh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.User;

public class UserDAO {
	/**
	 * Function to check user login by email and password
	 * @param email
	 * @param password
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ? AND DELETED = 0";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public User findByEmail(String email) {
		String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND DELETED = 0";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Function to get all users and subadmins
	 * @return
	 */
	public List<User> getAllSubadminAndUserAccount() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM USERS WHERE (ROLE = 'Subadmin' OR ROLE = 'User') AND DELETED = 0";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
					users.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public List<User> getAllSubadminAccount() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM USERS WHERE ROLE = 'Subadmin' AND DELETED = 0 AND STATUS = 1";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
					users.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public List<User> getAllUserAccount() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM USERS WHERE ROLE = 'User' AND DELETED = 0 AND STATUS = 1";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
					users.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Function to active or counteract user
	 * @param userId
	 * @param checked
	 * @return
	 */
	public boolean updateUserStatus(int userId, boolean checked) {
	    String sql = "UPDATE USERS SET STATUS = ? WHERE USER_ID = ? AND DELETED = 0";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, checked ? 1 : 0);
	        stmt.setInt(2, userId);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * Function to create a new user
	 * @param user
	 * @return
	 */
	public boolean newUser(User user) {
		String sql = "INSERT INTO USERS(USER_ID, USERNAME, GENDER, BIRTHDATE, PHONE, EMAIL, PASSWORD, ROLE, STATUS, DELETED) VALUES (USERS_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 1, 0)";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setInt(2, user.getGender());
			stmt.setDate(3, user.getBirthdate());
			stmt.setString(4, user.getPhone());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getPassword());
			stmt.setString(7, user.getRole());
			
			int rowInserted = stmt.executeUpdate();
			return rowInserted > 0;
		} catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * Function to check is email exists
	 * @param email
	 * @return
	 */
	public boolean isEmailExists(String email) {
		String sql = "SELECT COUNT(*) AS COUNT FROM USERS WHERE EMAIL = ? AND DELETED = 0";
		boolean exists = false;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt("COUNT");
					return exists = count>0;
				}
			}
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		return exists;
	}
	
	public boolean deleteUser(int userId) {
	    String sql = "UPDATE USERS SET DELETED = 1 WHERE USER_ID = ? AND DELETED = 0";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        
	        int rowsDeleted = stmt.executeUpdate();
	        return rowsDeleted > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public User getUserById(int userId) {
		User user = new User();
		String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setBirthdate(rs.getDate("BIRTHDATE"));
					user.setGender(rs.getInt("GENDER"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setPhone(rs.getString("PHONE"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getInt("STATUS"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean updateUser(User user) {
	    String sql = "UPDATE USERS SET USERNAME = ?, GENDER = ?, BIRTHDATE = ?, PHONE = ?, EMAIL = ?, ROLE = ? WHERE USER_ID = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, user.getUsername());
			stmt.setInt(2, user.getGender());
			stmt.setDate(3, user.getBirthdate());
			stmt.setString(4, user.getPhone());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getRole());
			stmt.setInt(7, user.getUserId());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean changePassword(String password, int userId) {
		String sql = "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?";
		try (Connection conn = ConnectionProvider.getConnection();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, password);
			stmt.setInt(2, userId);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}