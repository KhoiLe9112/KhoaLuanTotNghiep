<%@page import="com.dhkh.util.Constant"%>
<%@page import="com.dhkh.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<aside id="leftsidebar" class="sidebar">
	<!-- User Info -->
	<div class="user-info">
		<div class="image">
			<img src="shared/images/user.png" width="48" height="48" alt="User" />
		</div>
		<div class="info-container">
			<%
			User loggedInUser = (User) session.getAttribute("loggedInUser");
	        if (loggedInUser != null) {
	            String name = loggedInUser.getUsername();
	            String email = loggedInUser.getEmail();
	            int userId = loggedInUser.getUserId();
	        %>
			<div class="name" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"><%=name %></div>
			<div class="email"><%=email %></div>
			<input type="hidden" id="loggedInUserId" value="<%=userId %>" />
			<div class="btn-group user-helper-dropdown">
				<i class="material-icons" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
				<ul class="dropdown-menu pull-right">
					<li><a href="<s:url action='viewProfile' />"><i
							class="material-icons">person</i>Hồ sơ</a></li>
					<li role="separator" class="divider"></li>
					<li><a id="changePasswordButton" href="<s:url action='changePasswordInput' />"><i
							class="material-icons">vpn_key</i>Đổi mật khẩu</a></li>
					<li role="separator" class="divider"></li>
					<li><a id="logoutButton" href="<s:url action='logout' />"><i
							class="material-icons">input</i>Đăng xuất</a></li>
				</ul>
			</div>
			<%
	        } 
	        %>
		</div>
	</div>
	<!-- #User Info -->
	<!-- Menu -->
	<div class="menu">
		<ul class="list">
			<li class="header">MENU</li>
			<li><a href="<s:url action='postManage' />"> <i class="material-icons">event_available</i> <span>Thông báo</span>
			</a></li>
			<%
			if (loggedInUser != null) {
				String role = loggedInUser.getRole();
				if (Constant.ROLE_ADMIN.equals(role)) {
			%>
			<li><a href="<s:url action='userManage' />"> <i class="material-icons">person</i> <span>Quản lý người dùng</span>
			</a></li>
			
			<%
				} if (Constant.ROLE_ADMIN.equals(role) || Constant.ROLE_SUBADMIN.equals(role)) {
			%>
			<li><a href="<s:url action='subadminAssign' />"> <i class="material-icons">assignment</i> <span>Phân lịch</span>
			</a></li>
			<li><a href="<s:url action='statisticManage' />"> <i class="material-icons">insert_chart</i> <span>Thống kê</span>
			</a></li>
			<%
				}
			}
			%>
			<li><a href="<s:url action='roomManage' />"> <i class="material-icons">business</i> <span>Quản lý phòng</span>
			</a></li>
			<li><a href="<s:url action='reservations' />"> <i class="material-icons">schedule</i> <span>Đặt chỗ</span>
			</a></li>
		</ul>
	</div>
	<!-- #Menu -->
	<!-- Footer -->
	<div class="legal">
		<div class="copyright">
			&copy; 2023 - 2024 <a href="https://www.facebook.com/profile.php?id=100013828233734">Lab Tracker - Lê
				Minh Khôi</a>.
		</div>
		<div class="version">
			<b>Version: </b> 1.0.0
		</div>
	</div>
	<!-- #Footer -->
</aside>