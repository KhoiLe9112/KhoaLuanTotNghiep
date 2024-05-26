<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>LabTracker</title>

<%@ include file="components/template-links.jsp"%>
</head>

<body class="theme-indigo">
	<!-- Page Loader -->
	<%@ include file="components/page-loader.jsp"%>
	<!-- #END# Page Loader -->

	<!-- Top Bar -->
	<%@ include file="components/top-bar.jsp"%>
	<!-- #Top Bar -->

	<section>
		<!-- Left Sidebar -->
		<%@ include file="components/side-bar.jsp"%>
		<!-- #END# Left Sidebar -->
	</section>

	<section class="content">
		<div class="container-fluid">
			<%
	        if (loggedInUser != null) {
	            String name = loggedInUser.getUsername();
	            String email = loggedInUser.getEmail();
	            String role = loggedInUser.getRole();
	            Date birthdate = loggedInUser.getBirthdate();
	            int gender = loggedInUser.getGender();
	            String phone = loggedInUser.getPhone();
	        %>
			<div class="col-xs-12 col-sm-3">
				<div class="card profile-card">
					<div class="profile-header">&nbsp;</div>
					<div class="profile-body">
						<div class="image-area">
							<img src="shared/images/user.png" alt="AdminBSB - Profile Image" />
						</div>
						<div class="content-area">
							<h3><%=name %></h3>
							<p><%=email %></p>
							<p><%=role %></p>
						</div>
					</div>
					<%-- <div class="profile-footer">
						<ul>
							<li><span>Followers</span> <span>1.234</span></li>
							<li><span>Following</span> <span>1.201</span></li>
							<li><span>Friends</span> <span>14.252</span></li>
						</ul>
						<button class="btn btn-primary btn-lg waves-effect btn-block">FOLLOW</button>
					</div> --%>
				</div>
			</div>

			<div class="col-xs-12 col-sm-9">
				<div class="card">
					<div class="body">
						<div>
							<form class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">Họ tên</label>
									<div class="col-sm-10">
										<div class="form-line">
											<input type="text" class="form-control" id="username"
												name="user.username" placeholder="Họ tên"
												value="<%=name %>" disabled>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Email</label>
									<div class="col-sm-10">
										<div class="form-line">
											<input type="email" class="form-control" id="email"
												name="user.email" placeholder="Email" value="<%=email %>"
												disabled>
										</div>
									</div>
								</div>
								<div class="form-group demo-masked-input">
									<label class="col-sm-2 control-label">Ngày sinh</label>
									<div class="col-sm-10">
										<div class="form-line">
											<input type="text" class="form-control date" id="birthdate"
												name="user.birthdate" value="<%=birthdate%>"
												placeholder="Ex: 2002-11-09">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Giới tính</label>
									<div class="col-sm-10">
										<div class="form-line">
											<input type="text" class="form-control" id="gender"
												name="user.gender" placeholder="Giói tính" value="<%= (gender == 1) ? "Nam" : "Nữ" %>"
												disabled>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Số điện thoại</label>
									<div class="col-sm-10">
										<div class="form-line">
											<input type="text" class="form-control" id="phone"
												name="user.phone" placeholder="Số điện thoại" value="<%=phone %>">
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn bg-indigo waves-effect">XÁC NHẬN</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>
			<!-- #END# Content -->
		</div>
	</section>

	<%@ include file="components/template-scripts.jsp"%>
</body>

</html>
