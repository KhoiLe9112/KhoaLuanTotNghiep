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
	
	<%@ include file="components/template-links.jsp" %>
</head>

<body class="theme-indigo">
	<!-- Page Loader -->
	<%@ include file="components/page-loader.jsp" %>
	<!-- #END# Page Loader -->

	<!-- Top Bar -->
	<%@ include file="components/top-bar.jsp" %>
	<!-- #Top Bar -->

	<section>
		<!-- Left Sidebar -->
		<%@ include file="components/side-bar.jsp" %>
		<!-- #END# Left Sidebar -->
	</section>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<h2>QUẢN LÝ NGƯỜI DÙNG</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<!-- Header -->
						<div class="header">
							<h2>CẬP NHẬT NGƯỜI DÙNG</h2>							
						</div>
						<!-- #END# Header -->
						<!-- Body -->
						<div class="body">
							<s:if test="hasActionErrors()">
			            		<div class="errors">
			            			<s:actionerror />
			            		</div>
			            	</s:if>  
							<form action="updateUser" method="post" id="newUserForm">
								<input type="hidden" name="user.userId" value="${user.userId}"/>
								<div class="row clearfix">
									<div class="col-sm-6">
										<label for="username">Họ tên</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
	                                            <input type="text" class="form-control" id="username" name="user.username" 
	                                            	value="${user.username}" maxlength="100" 
	                                            	placeholder="Nhập họ tên..." autofocus />
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-sm-3">
										<label for="birthdate">Ngày sinh</label>
	                                    <div class="form-group">
	                                        <div class="form-line" id="bs_datepicker_container">
												<input type="text" class="form-control" id="birthdate"
													name="user.birthdateStr" data-date-format="dd/mm/yyyy"
													value="<s:property value='user.birthdateStr' />"
													placeholder="Chọn ngày sinh...">
											</div>
	                                    </div>
	                                </div>
	                                <div class="col-sm-3">
										<label for="gender">Giới tính</label>
	                                    <div class="form-group">
	                                       	<input type="radio" name="user.gender" id="male" class="with-gap" value="1" 
	                                       		<s:if test="%{user.gender == 1}">checked</s:if>>
		                                   	<label for="male">Nam</label>
		
		                                   	<input type="radio" name="user.gender" id="female" class="with-gap" value="0" 
		                                   		<s:if test="%{user.gender == 0}">checked</s:if>>
		                                	<label for="female" class="m-l-20">Nữ</label>
	                                    </div>
	                                </div>
								</div>
								<div class="row clearfix">
									<div class="col-sm-3">
										<label for="phone">Số điện thoại</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
	                                            <input type="text" class="form-control" id="phone" name="user.phone" 
	                                            	value="<s:property value='user.phone' />" maxlength="12"
	                                            	placeholder="Nhập số điện thoại..." />
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-sm-6">
										<label for="email">Email</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
	                                            <input type="text" class="form-control" id="email" name="user.email" 
	                                            	value="<s:property value='user.email' />" maxlength="25" 
	                                            	placeholder="Nhập địa chỉ email..." />
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-sm-3">
										<label for="role">Phân quyền</label>
	                                    <div class="form-group">
	                                        <select class="form-control show-tick" id="role" data-live-search="true" name="user.role">
		                                        <option value="">-- Chọn phân quyền --</option>
		                                        <option value="Admin" <s:if test="%{user.role == 'Admin'}">selected</s:if>>
		                                        	Admin
		                                        </option>
		                                        <option value="Subadmin" <s:if test="%{user.role == 'Subadmin'}">selected</s:if>>
		                                        	Subadmin
		                                        </option>
		                                        <option value="User" <s:if test="%{user.role == 'User'}">selected</s:if>>
		                                        	User
		                                        </option>
	                                    	</select>
	                                    </div>
	                                </div>
								</div>
								
								<button type="button" class="btn bg-grey waves-effect" id="btnBack">Quay lại</button>
								<button type="submit" class="btn bg-indigo waves-effect">Xác nhận</button>
								
							</form>						
						</div>
						<!-- #END# Body -->
					</div>
				</div>
			</div>
			<!-- #END# Content -->
		</div>
	</section>

	<%@ include file="components/template-scripts.jsp" %>
	
	<!-- Script check valid newUserForm -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/userManage/validNewUser.js"></script>
	
	<script>
		$('#btnBack').click(function() {
			window.location.href = "<s:url action='userManage'/>";
		})
	</script>
</body>

</html>
