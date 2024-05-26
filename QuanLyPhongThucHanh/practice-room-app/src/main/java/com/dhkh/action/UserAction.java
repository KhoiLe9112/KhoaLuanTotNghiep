package com.dhkh.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dhkh.dao.UserDAO;
import com.dhkh.model.User;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private List<User> users;
	private User user;
	private int userId;
	private String oldPassInput;
	private String newPassInput;
	
	private boolean stt;
	private String msg;
	private String data;
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * Function to get all users and subadmins
	 */
	public String execute() {
		UserDAO userDAO = new UserDAO();
		users = userDAO.getAllSubadminAndUserAccount();
		return SUCCESS;
	}
	
	/**
	 * Function to get user information in session if exists
	 * @return
	 */
	public String getNewUserInput() {
		HttpSession session = request.getSession();
		User userInput = (User) session.getAttribute("newUserInput");

        if (userInput != null) {
            this.user = userInput;
        }
        session.removeAttribute("newUserInput");
        return SUCCESS;
    }

	/**
	 * Function to update user status
	 * @return
	 */
	public String updateUserStatus() {
		int userId = Integer.parseInt(request.getParameter("userId"));
	    boolean checked = Boolean.parseBoolean(request.getParameter("checked"));
	    
        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateUserStatus(userId, checked);
        
        if (updated) {
        	this.setStt(true);
        	this.setMsg("Cập nhật trạng thái người dùng thành công.");     
        } else {
        	this.setStt(false);
        	this.setMsg("Cập nhật trạng thái người dùng không thành công.");
        }
        
        return SUCCESS;
    }
	
	/**
	 * Function to create new user
	 * @return
	 */
	public String addNewUser() {
		try {
	        UserDAO userDAO = new UserDAO();
	        User userInput = getUser();
	        
	        if (userDAO.isEmailExists(userInput.getEmail())) {
	        	addActionError("Email đã tồn tại");
	        	return ERROR;
	        }
	        
	        // Chuyển đổi ngày
	        java.util.Date javaBirthdate = new SimpleDateFormat("dd/MM/yyyy").parse(userInput.getBirthdateStr());
	        java.sql.Date sqlBirthdate = new java.sql.Date(javaBirthdate.getTime());
	        userInput.setBirthdate(sqlBirthdate);
	        
	        // Lưu thông tin vào session
	        HttpSession session = request.getSession();
	        session.setAttribute("newUserInput", userInput);
	        
	        // Tạo mật khẩu ngẫu nhiên
	        int length = 12;
	        boolean includeLowercase = true;
	        boolean includeUppercase = true;
	        boolean includeNumbers = true;
	        boolean includeSpecialCharacters = false;
	        String randomPassword = RandomPasswordGenerator.generateRandomPassword(length, includeLowercase, includeUppercase, includeNumbers, includeSpecialCharacters);
	        userInput.setPassword(randomPassword);
	        
	        userDAO.newUser(userInput);
	        userInput.setUserId(userDAO.findByEmailAndPassword(userInput.getEmail(), randomPassword).getUserId());
	        
	        // Thêm thông báo mật khẩu mặc định
	        NoticeAction.addPasswordNotification(randomPassword, userInput);
	        
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
	    }
	}
	
	public String deleteUser() {
		try {
			UserDAO userDAO = new UserDAO();
			int userIdInput = getUserId();
			
			userDAO.deleteUser(userIdInput);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
		}	
	}
	
	public String getUserById() {
		try {			
			UserDAO userDAO = new UserDAO();
			int userIdInput = getUserId();			
			User user = userDAO.getUserById(userIdInput);
			
			if (user.getBirthdate() != null) {
				java.sql.Date sqlBirthdate = user.getBirthdate();
				java.util.Date javaBirthdate = new java.util.Date(sqlBirthdate.getTime());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String birthdateStr = dateFormat.format(javaBirthdate);
				user.setBirthdateStr(birthdateStr);
			}
			
			setUser(user);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
		}	
	}
	
	public String getUserJsonById() {
		try {			
			UserDAO userDAO = new UserDAO();
			int userIdInput = getUserId();			
			User user = userDAO.getUserById(userIdInput);
			
			Gson gson = new Gson();
			String jsonUsers = gson.toJson(user);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonUsers);
			
			this.setStt(true);
			this.setData(jsonUsers.toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setStt(false);
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
		}
		return SUCCESS;
	}
	
	public String updateUser() {
		try {
	        UserDAO userDAO = new UserDAO();
	        User userInput = getUser();
	        User oldUser = userDAO.getUserById(userInput.getUserId());
	        String oldEmail = oldUser.getEmail();
	        
	        if (!oldEmail.equals(userInput.getEmail()) && userDAO.isEmailExists(userInput.getEmail())) {
	        	addActionError("Email đã tồn tại");
	        	return ERROR;
	        }
	        
	        // Chuyển đổi ngày
	        java.util.Date javaBirthdate = new SimpleDateFormat("dd/MM/yyyy").parse(userInput.getBirthdateStr());
	        java.sql.Date sqlBirthdate = new java.sql.Date(javaBirthdate.getTime());
	        userInput.setBirthdate(sqlBirthdate);
	        
	        userDAO.updateUser(userInput);
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
	    }
	}
	
	public String changePassword() {
		try {
	        UserDAO userDAO = new UserDAO();
	        User oldUser = userDAO.getUserById(userId);
	        String oldPassword = oldUser.getPassword();
	        
	        if (!oldPassword.equals(oldPassInput)) {
	        	addActionError("Mật khẩu cũ không hợp lệ");
	        	return ERROR;
	        }
	        if (oldPassword.equals(newPassInput)) {
	        	addActionError("Mật khẩu mới không không được trùng mật khẩu cũ");
	        	return ERROR;
	        }
	        
	        userDAO.changePassword(newPassInput, userId);
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
	    }
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOldPassInput() {
		return oldPassInput;
	}

	public void setOldPassInput(String oldPassInput) {
		this.oldPassInput = oldPassInput;
	}

	public String getNewPassInput() {
		return newPassInput;
	}

	public void setNewPassInput(String newPassInput) {
		this.newPassInput = newPassInput;
	}

	public boolean isStt() {
		return stt;
	}

	public void setStt(boolean stt) {
		this.stt = stt;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String string) {
		this.msg = string;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}