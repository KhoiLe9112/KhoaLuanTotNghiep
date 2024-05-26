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
			<div class="block-header">
				<h2>QUẢN LÝ NGƯỜI DÙNG</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<!-- Header -->
						<div class="header">
							<h2>THÔNG TIN NGƯỜI DÙNG</h2>
						</div>
						<!-- #END# Header -->
						<!-- Body -->
						<div class="body">
							<div class="row clearfix">
								<div class="col-sm-6">
									<label for="username">Họ tên</label>
									<div class="form-group">
										<div class="form-line">
											<p>${user.username }</p>
										</div>
									</div>
								</div>
								<div class="col-sm-3">
									<label for="birthdate">Ngày sinh</label>
									<div class="form-group">
										<div class="form-line">
											<p>${user.birthdateStr }</p>
										</div>
									</div>
								</div>
								<div class="col-sm-3">
									<label for="gender">Giới tính</label>
									<div class="form-group">
										<div class="form-line">
											<s:if test="%{user.gender == 1}"><p>Nam</p></s:if>
											<s:if test="%{user.gender == 0}"><p>Nữ</p></s:if>
										</div>
									</div>
								</div>
							</div>
							<div class="row clearfix">
								<div class="col-sm-3">
									<label for="phone">Số điện thoại</label>
									<div class="form-group">
										<div class="form-line">
											<p>${user.phone }</p>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<label for="email">Email</label>
									<div class="form-group">
										<div class="form-line">
											<p>${user.email }</p>
										</div>
									</div>
								</div>
								<div class="col-sm-3">
									<label for="role">Phân quyền</label>
									<div class="form-group">
										<div class="form-line">
											<s:if test="%{user.role == 'Admin'}"><p>Admin</p></s:if>
											<s:if test="%{user.role == 'Subadmin'}"><p>Subadmin</p></s:if>
											<s:if test="%{user.role == 'User'}"><p>User</p></s:if>
										</div>
									</div>
								</div>
							</div>

							<button type="button" class="btn bg-grey waves-effect"
								id="btnBack">Quay lại</button>

						</div>
						<!-- #END# Body -->
					</div>
				</div>
			</div>
			<!-- #END# Content -->
		</div>
	</section>

	<%@ include file="components/template-scripts.jsp"%>

	<script>
		$('#btnBack').click(function() {
			window.location.href = "<s:url action='userManage'/>";
		})
	</script>
</body>

</html>
