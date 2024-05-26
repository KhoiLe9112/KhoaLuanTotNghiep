package com.dhkh.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import com.dhkh.dao.PostDAO;
import com.dhkh.model.Post;
import com.dhkh.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class PostAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private int postId;
	private List<Post> posts;
	private Post post;
	
	public String execute() {
		PostDAO postDAO = new PostDAO();
		posts = postDAO.getAllPosts();
		
		// Chuyển timestamp sang string
		for (Post p : posts) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm]");
			java.sql.Timestamp PostTime = p.getPostTime();
			String postTimeStr = dateFormat.format(PostTime);
			p.setPostTimeStr(postTimeStr);
		}
		
		return SUCCESS;
	}
	
	public String viewPost() {
		try {
			PostDAO postDAO = new PostDAO();
			this.post = postDAO.getPostById(this.getPostId());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm]");
			java.sql.Timestamp PostTime = this.post.getPostTime();
			String postTimeStr = dateFormat.format(PostTime);
			this.post.setPostTimeStr(postTimeStr);
		} catch (Exception e) {
			e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
		}
		
		return SUCCESS;
	}
	
	public String newPost() {
		try {
			PostDAO postDAO = new PostDAO();
			Post postInput = getPost();
			
			// Kiểm tra và mã hóa dữ liệu đầu vào
		    PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.LINKS);
		    String sanitizedContent = policy.sanitize(postInput.getContent());
		    postInput.setContent(sanitizedContent);
		    String sanitizedTitle = policy.sanitize(postInput.getTitle());
		    postInput.setTitle(sanitizedTitle);
		    
		    // Kiểm tra xem liệu dữ liệu đã được mã hóa hay không
		    if (StringUtil.isNullOrEmpty(postInput.getTitle())) {
		        // Dữ liệu đã bị thay đổi, có thể chứa mã độc
		    	addActionError("Tiêu đề không hợp lệ.");
		        return ERROR;
		    }
			
			postDAO.newPost(postInput);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
		}
	}
	
	public String deletePost() {
		try {
			PostDAO postDAO = new PostDAO();
			postDAO.deletePost(this.getPostId());
		} catch (Exception e) {
			e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
		}
		
		return SUCCESS;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
}