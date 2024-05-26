package com.dhkh.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dhkh.dao.SubScheduleDAO;
import com.dhkh.dao.UserDAO;
import com.dhkh.model.SubSchedule;
import com.dhkh.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class SubScheduleAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private List<SubSchedule> subSchedules;
	private SubSchedule subSchedule;
	private List<User> subadmins;
	
	private HttpServletRequest request;
	
	public String execute() {
		try {
	        SubScheduleDAO subScheduleDAO = new SubScheduleDAO();
	        setSubSchedules(subScheduleDAO.getAllSubSchedule());
	        
	        UserDAO userDAO = new UserDAO();
	        setSubadmins(userDAO.getAllSubadminAccount());
	        
	        HttpSession session = request.getSession();
	        String errorMessage = (String) session.getAttribute("errorMessage");
	        if (errorMessage != null) {
	            addActionError(errorMessage);
	            session.removeAttribute("errorMessage");
	        }
	        
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        addActionError("Có lỗi xảy ra, vui lòng thử lại");
	        return ERROR;
	    }
	}
	
	public String newSubadminAssign() {
		try {
			SubScheduleDAO subScheduleDAO = new SubScheduleDAO();
			SubSchedule subScheduleInput = getSubSchedule();
			
			// Chuyển đổi ngày
	        java.util.Date javaAssignDate = new SimpleDateFormat("dd/MM/yyyy").parse(subScheduleInput.getAssignDateStr());
	        java.sql.Date sqlAssignDate = new java.sql.Date(javaAssignDate.getTime());
	        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
	        subScheduleInput.setAssignDate(sqlAssignDate);
	        
	        if (sqlAssignDate.compareTo(currentDate) < 0) {
	        	HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", "Ngày phân công phải lớn hơn hoặc bằng ngày hiện tại.");
	            return ERROR;
	        }
	        
	        if (subScheduleDAO.isAssignDateExists(subScheduleInput.getAssignDate(), subScheduleInput.getPeriod())) {
	        	HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", "Đã có subadmin được phân vào ngày này.");
	        	return ERROR;
	        }
	        
	        subScheduleDAO.newSubSchedule(subScheduleInput);
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
	        return ERROR;
	    }
	}

	public List<SubSchedule> getSubSchedules() {
		return subSchedules;
	}

	public void setSubSchedules(List<SubSchedule> subSchedules) {
		this.subSchedules = subSchedules;
	}

	public List<User> getSubadmins() {
		return subadmins;
	}

	public void setSubadmins(List<User> subadmins) {
		this.subadmins = subadmins;
	}

	public SubSchedule getSubSchedule() {
		return subSchedule;
	}

	public void setSubSchedule(SubSchedule subSchedule) {
		this.subSchedule = subSchedule;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}