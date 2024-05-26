package com.dhkh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.Notice;

public class NoticeDAO {
	/**
	 * Function to get all notices from DTB
	 */
	public List<Notice> getAllNotices() {
		List<Notice> notices = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    n.*, ")
			.append("    u.username ")
			.append("FROM ")
			.append("         notice n ")
			.append("    JOIN users u ON n.user_id = u.user_id ")
			.append("ORDER BY ")
			.append("    create_date DESC ");
		String sql = sqlBuilder.toString();	

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Notice notice = new Notice();
				notice.setNoticeId(rs.getInt("NOTICE_ID"));
				notice.setTitle(rs.getString("TITLE"));
				notice.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				notice.setContent(rs.getString("CONTENT"));
				notice.setUserId(rs.getInt("USER_ID"));
				notice.setFullName(rs.getString("USERNAME"));
				notice.setType(rs.getString("TYPE"));
				notices.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notices;
	}
	
	/**
	 * Function to add new notice
	 * @param notice
	 * @return
	 */
	public boolean addNotice(Notice notice) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("INSERT INTO NOTICE(NOTICE_ID, TITLE, CONTENT, USER_ID, TYPE)")
		    .append("VALUES( ")
		    .append("    NOTICE_SEQ.NEXTVAL,  ")
		    .append("    ?, ")
		    .append("    ?, ")
		    .append("    ?, ")
		    .append("    ? ")
		    .append("    ) ");
		String sql = sqlBuilder.toString();		
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, notice.getTitle());
			stmt.setString(2, notice.getContent());
			stmt.setInt(3, notice.getUserId());
			stmt.setString(4, notice.getType());
			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Notice getNoticeById(int noticeId) {
		Notice notice = new Notice();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    n.*, ")
			.append("    u.username ")
			.append("FROM ")
			.append("         notice n ")
			.append("    JOIN users u ON n.user_id = u.user_id ")
			.append("WHERE ")
			.append("    notice_id = ? ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, noticeId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					notice.setNoticeId(rs.getInt("NOTICE_ID"));
					notice.setTitle(rs.getString("TITLE"));
					notice.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					notice.setContent(rs.getString("CONTENT"));
					notice.setUserId(rs.getInt("USER_ID"));
					notice.setFullName(rs.getString("USERNAME"));
					notice.setType(rs.getString("TYPE"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}
	
	public List<Notice> getNoticeByUser(int userId) {
		List<Notice> notices = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT ")
			.append("    n.*, ")
			.append("    u.username ")
			.append("FROM ")
			.append("         notice n ")
			.append("    JOIN users u ON n.user_id = u.user_id ")
			.append("WHERE ")
			.append("    n.user_id = ? ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Notice notice = new Notice();
					notice.setNoticeId(rs.getInt("NOTICE_ID"));
					notice.setTitle(rs.getString("TITLE"));
					notice.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					notice.setContent(rs.getString("CONTENT"));
					notice.setUserId(rs.getInt("USER_ID"));
					notice.setFullName(rs.getString("USERNAME"));
					notice.setType(rs.getString("TYPE"));
					notices.add(notice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notices;
	}
}