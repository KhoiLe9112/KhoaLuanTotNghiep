package com.dhkh.action;

import java.util.Map;

import com.dhkh.model.User;
import com.dhkh.util.Constant;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected boolean isUser() {
	    Map<String, Object> session = getSession();
	    User loggedInUser = (User) session.get("loggedInUser");
	    return loggedInUser != null && loggedInUser.getRole().contains(Constant.ROLE_USER);
	}
	
	protected boolean isSubadmin() {
	    Map<String, Object> session = getSession();
	    User loggedInUser = (User) session.get("loggedInUser");
	    return loggedInUser != null && loggedInUser.getRole().contains(Constant.ROLE_SUBADMIN);
	}
	
	protected boolean isAdmin() {
	    Map<String, Object> session = getSession();
	    User loggedInUser = (User) session.get("loggedInUser");
	    return loggedInUser != null && loggedInUser.getRole().contains(Constant.ROLE_ADMIN);
	}

	private Map<String, Object> getSession() {
		return org.apache.struts2.ServletActionContext.getContext().getSession();
	}
	
	
}