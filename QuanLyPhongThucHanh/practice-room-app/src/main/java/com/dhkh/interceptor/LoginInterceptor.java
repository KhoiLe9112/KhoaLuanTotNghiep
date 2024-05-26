package com.dhkh.interceptor;

import com.dhkh.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		User us = (User) context.getSession().get("loggedInUser");
		
		if (us != null) {
			return invocation.invoke();
		} else {
			return "loginInput";
		}
	}
}