<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<h2>ĐỔI MẬT KHẨU</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<!-- Header -->
						<div class="header">
							<h2>ĐỔI MẬT KHẨU</h2>
						</div>
						<!-- #END# Header -->
						<!-- Body -->
						<div class="body">
							<s:if test="hasActionErrors()">
			            		<div class="errors">
			            			<s:actionerror />
			            		</div>
			            	</s:if>
			            	<form action="changePassword" method="post" id="changePasswordForm">
			            		<%
		                        if (loggedInUser != null) {
		                        	int userId = loggedInUser.getUserId();
		                        %>
			            		<input type="hidden" name="userId" value="<%=userId %>"/>
			            		<%
		                        }
			            		%>
								<div class="row clearfix">
									<div class="col-sm-12">
										<label>Mật khẩu cũ</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
	                                            <input type="password" class="form-control" id="oldPassInput" name="oldPassInput" 
	                                            	value="<s:property value='oldPassInput' />" maxlength="25" 
	                                            	placeholder="Nhập mật khẩu cũ..." autofocus />
	                                        </div>
	                                    </div>
	                                </div>
								</div>
								<div class="row clearfix">
									<div class="col-sm-12">
										<label>Mật khẩu mới</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
												<input type="password" class="form-control" id="newPassInput" name="newPassInput" 
	                                            	value="<s:property value='newPassInput' />" maxlength="25" 
	                                            	placeholder="Nhập mật khẩu mới..." autofocus />
											</div>
	                                    </div>
	                                </div>
								</div>
								<div class="row clearfix">
									<div class="col-sm-12">
										<label>Nhập lại mật khẩu mới</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
												<input type="password" class="form-control" id="confirmNewPass" name="confirmNewPass" 
	                                            	value="<s:property value='confirmNewPass' />" maxlength="25" 
	                                            	placeholder="Nhập lại mật khẩu mới..." autofocus />
											</div>
	                                    </div>
	                                </div>
								</div>
								
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
	
	<!-- Script check valid change password form -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/validChangePass.js"></script>

</body>

</html>
