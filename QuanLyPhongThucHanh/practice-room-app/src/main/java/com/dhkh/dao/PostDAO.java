package com.dhkh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhkh.config.ConnectionProvider;
import com.dhkh.model.Post;

public class PostDAO {
	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<>();
		//String sql = "SELECT * FROM (SELECT * FROM POST ORDER BY POST_TIME DESC) WHERE ROWNUM <= 20";
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
			.append("SELECT * FROM ( ")
			.append("    SELECT * FROM POST ORDER BY POST_TIME DESC ")
			.append(") WHERE ROWNUM <= 20 ");
		String sql = sqlBuilder.toString();
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Post post = new Post();
					post.setPostId(rs.getInt("POST_ID"));
					post.setTitle(rs.getString("TITLE"));
					post.setContent(rs.getString("CONTENT"));
					post.setPostTime(rs.getTimestamp("POST_TIME"));
					post.setUserId(rs.getInt("USER_ID"));
					posts.add(post);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	public Post getPostById(int postId) {
		Post post = new Post();
		String sql = "SELECT * FROM POST WHERE POST_ID = ?";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, postId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					post.setPostId(rs.getInt("POST_ID"));
					post.setTitle(rs.getString("TITLE"));
					post.setContent(rs.getString("CONTENT"));
					post.setPostTime(rs.getTimestamp("POST_TIME"));
					post.setUserId(rs.getInt("USER_ID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}
	
	public boolean newPost(Post post) {
		boolean inserted = false;
		String sql = "INSERT INTO POST (POST_ID, TITLE, CONTENT, USER_ID) VALUES (POST_SEQ.NEXTVAL, ?, ?, ?)";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setInt(3, post.getUserId());
			int rowsInserted = stmt.executeUpdate();
			inserted = rowsInserted > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inserted;
	}
	
	public boolean deletePost(int postId) {
		boolean deleted = false;
		String sql = "DELETE FROM POST WHERE POST_ID = ?";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, postId);
			int rowDeleted = stmt.executeUpdate();
			deleted = rowDeleted > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleted;
	}
}