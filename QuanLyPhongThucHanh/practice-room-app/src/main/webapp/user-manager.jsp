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
				<h2>QUẢN LÝ NGƯỜI DÙNG</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<div class="header">
							<h2>
								QUẢN LÝ NGƯỜI DÙNG
								<button type="button" class="btn bg-indigo waves-effect" id="btnNewUserInput" style="float: right;">Thêm người dùng</button>
							</h2>							
						</div>
						<div class="body">
							<!-- Table -->
							<div class="table-responsive">
								<table
									class="table table-bordered table-striped table-hover js-basic-example dataTable">
									<thead>
										<tr>
											<th>Họ tên</th>
											<th>Email</th>
											<th>Phân quyền</th>
											<th>Kích hoạt</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="users" var="user" status="counter">
											<tr>
												<td><s:property value="#user.username" /></td>
												<td><s:property value="#user.email" /></td>
												<td><s:property value="#user.role" /></td>
												<td style="text-align: center;">
													<div class="switch">
													    <label>
													        <input type="checkbox" class="userStatusCheckbox" id="userStatus_<s:property value="#user.userId" />" 
												                   <s:property value="#user.status == 1 ? 'checked' : ''" />>
												            <span class="lever switch-col-indigo"></span>
													    </label>
													</div>
												</td>
												<td style="text-align: center;">
													<input type="hidden" id="userId" name="user.userId" value="<s:property value="#user.userId" />">
													<button type="button" onclick="getUserDetails(<s:property value="#user.userId" />)" 
														class="btn bg-indigo waves-effect">
                                    					<i class="material-icons">info</i>
                                					</button>
                                					<button type="button" onclick="updateUserInput(<s:property value="#user.userId" />)" 
                                						class="btn bg-indigo waves-effect">
                                    					<i class="material-icons">update</i>
                                					</button>
                                					<button type="button" id="btnDeleteUser" onclick="deleteUser(<s:property value="#user.userId" />)" 
                                						class="btn bg-indigo waves-effect">
                                    					<i class="material-icons">delete</i>
                                					</button>
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							<!-- #END# Table -->
						</div>
					</div>
				</div>
			</div>
			<!-- #END# Content -->
		</div>
	</section>

	<%@ include file="components/template-scripts.jsp" %>
	
	<script>
		$('#btnNewUserInput').click(function() {
			window.location.href = "<s:url action='newUserInput'/>";
		})
		
		function deleteUser(userId) {
			if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
				window.location.href = "<s:url action='deleteUser'/>?userId=" + userId;
			}
		}
		
		function updateUserInput(userId) {
			window.location.href = "<s:url action='updateUserInput'/>?userId=" + userId;
		}
		
		function getUserDetails(userId) {
			window.location.href = "<s:url action='getUserDetails'/>?userId=" + userId;
		}
	
		$(document).ready(function() {
		    $(".userStatusCheckbox").change(function() {
		    	var checkbox = $(this);
		    	var initialChecked = checkbox.data("initialChecked");
		        var userId = $(this).attr("id").split("_")[1];
		        var checked = $(this).is(":checked");
		        var alrt = "";
		        
		        if (checked == true) {
		        	alrt = 'Bạn có chắc chắn muốn kích hoạt tài khoản này?';
		        } else {
		        	alrt = 'Bạn có chắc chắn muốn vô hiệu hóa tài khoản này?';
		        }
		        
		        if (confirm(alrt)) {
		        	$.ajax({
		        		contentType: 'application/json',
		        		url: 'updateUserStatus',
			            method: "GET",
			            data: { userId: userId, checked: checked },
			            success: function(response) {
			               console.log("Yêu cầu Ajax thành công", response);
			               window.location.href = "<s:url action='userManage'/>";
			               alert('Thông báo: ' + response.message);
			            },
			            error: function(error) {
			                console.error("Lỗi khi cập nhật trạng thái người dùng:", error);
			            }
			        });
		        } else {
		        	checkbox.prop("checked", initialChecked);
		        }
		    });
		    
		 	// Lưu trạng thái ban đầu của checkbox khi trang web tải lên
		    $(".userStatusCheckbox").each(function() {
		        var checkbox = $(this);
		        var initialChecked = checkbox.is(":checked");
		        checkbox.data("initialChecked", initialChecked);
		    });
		});
	</script>
</body>

</html>
