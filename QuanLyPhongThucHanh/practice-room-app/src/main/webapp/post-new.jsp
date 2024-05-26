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
				<h2>THÔNG BÁO</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<!-- Header -->
						<div class="header">
							<h2>ĐĂNG THÔNG BÁO</h2>
						</div>
						<!-- #END# Header -->
						<!-- Body -->
						<div class="body">
							<s:if test="hasActionErrors()">
			            		<div class="errors">
			            			<s:actionerror />
			            		</div>
			            	</s:if>
			            	<form action="newPost" method="post" id="newPostForm">
			            		<%
		                        if (loggedInUser != null) {
		                        	int userId = loggedInUser.getUserId();
		                        %>
			            		<input type="hidden" name="post.userId" value="<%=userId %>"/>
			            		<%
		                        }
			            		%>
								<div class="row clearfix">
									<div class="col-sm-12">
										<label>Tiêu đề</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
	                                            <input type="text" class="form-control" id="title" name="post.title" 
	                                            	value="<s:property value='post.title' />" maxlength="150" 
	                                            	placeholder="Nhập tiêu đề..." autofocus />
	                                        </div>
	                                    </div>
	                                </div>
								</div>
								<div class="row clearfix">
									<div class="col-sm-12">
										<label>Nội dung</label>
	                                    <div class="form-group">
	                                        <div class="form-line">
												<textarea rows="4" class="form-control no-resize"
													id="content" name="post.content"
													value="<s:property value='post.content' />" maxlength="350"
													placeholder="Nhập nội dung..."></textarea>
											</div>
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
	
	<!-- Script check valid updateRoomForm -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/roomManage/validUpdateRoom.js"></script>
	
	<script>
		$('#btnBack').click(function() {
			window.location.href = "<s:url action='postManage'/>";
		})
	</script>

</body>

</html>
