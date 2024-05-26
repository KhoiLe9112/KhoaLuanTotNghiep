package com.dhkh.action;

import java.util.Map;

import com.dhkh.dao.UserDAO;
import com.dhkh.model.User;
import com.dhkh.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private User user;
	private String username;
	private String email;
	private String phone;
	
	private boolean stt;
	
	/**
	 * Function to check login
	 */
	public String execute() {
		UserDAO userDAO = new UserDAO();
		User userInput = getUser();
		User us = userDAO.findByEmailAndPassword(userInput.getEmail(), userInput.getPassword());
		
		if (StringUtil.isNullOrEmpty(userInput.getEmail()) || StringUtil.isNullOrEmpty(userInput.getPassword())) {
			addActionError("Vui lòng nhập email và mật khẩu");
			return INPUT;
		}
		if (us != null) {
			// Lưu thông tin đăng nhập vào session
			Map<String, Object> session = getSession();
			session.put("loggedInUser", us);
			
			return SUCCESS;
		} else {
			addActionError("Email hoặc mật khẩu không hợp lệ");
			return INPUT;
		}
	}
	
	public String loginWithGoogle() {
		UserDAO userDAO = new UserDAO();
		if (!userDAO.isEmailExists(email)) {
			User user = new User();
			user.setEmail(email);
			user.setPhone(phone);
			user.setUsername(username);
			user.setRole("User");
			
			// Tạo mật khẩu ngẫu nhiên
	        int length = 12;
	        boolean includeLowercase = true;
	        boolean includeUppercase = true;
	        boolean includeNumbers = true;
	        boolean includeSpecialCharacters = false;
	        String randomPassword = RandomPasswordGenerator.generateRandomPassword(length, includeLowercase, includeUppercase, includeNumbers, includeSpecialCharacters);
	        user.setPassword(randomPassword);
	        
	        userDAO.newUser(user);
	        user.setUserId(userDAO.findByEmailAndPassword(email, randomPassword).getUserId());
	        
	        // Thêm thông báo mật khẩu mặc định
	        NoticeAction.addPasswordNotification(randomPassword, user);
//	        Map<String, Object> session = getSession();
//		    User loggedInUser = (User) session.get("loggedInUser");
//		    if (loggedInUser != null) {
//		    	NoticeAction.addPasswordNotification(randomPassword, loggedInUser);
//		    }
		}
		User us = userDAO.findByEmail(email);
		
		if (us != null) {
			// Lưu thông tin đăng nhập vào session
			Map<String, Object> session = getSession();
			session.put("loggedInUser", us);
			setStt(true);
			
			return SUCCESS;
		} else {
			addActionError("Có lỗi xảy ra, vui lòng thử lại");
			setStt(false);
			
			return INPUT;
		}
	}
	
	/**
	 * Function to logout
	 * @return
	 */
	public String logout() {
		ActionContext.getContext().getSession().remove("loggedInUser");
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isStt() {
		return stt;
	}

	public void setStt(boolean stt) {
		this.stt = stt;
	}

	private Map<String, Object> getSession() {
		return org.apache.struts2.ServletActionContext.getContext().getSession();
	}
}