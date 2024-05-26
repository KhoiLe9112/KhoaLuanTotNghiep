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
				<h2>PHÂN LỊCH QUẢN LÝ PHÒNG</h2>
			</div>
			<!-- Content -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<div class="header">
							<h2>
								PHÂN LỊCH QUẢN LÝ PHÒNG
								<%
					            if (loggedInUser != null) {
					                String role = loggedInUser.getRole();
					                if (Constant.ROLE_ADMIN.equals(role)) {
					            %>
								<div class="button-demo js-modal-buttons" style="float: right;">
									<button type="button" data-color="indigo"
										class="btn bg-indigo waves-effect button-demo js-modal-buttons">Phân công</button>
								</div>
								<%
					            	}
					            }
								%>
							</h2>
						</div>
						<div class="body">
							<s:if test="hasActionErrors()">
			            		<div class="errors">
			            			<s:actionerror />
			            		</div>
			            	</s:if> 
							<!-- Table -->
							<div class="table-responsive">
								<table
									class="table table-bordered table-striped table-hover js-basic-example dataTable">
									<thead>
										<tr>
											<th>Ngày phân công</th>
											<th>Phiên</th>
											<th>Họ tên</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="subSchedules" var="subSchedule"
											status="counter">
											<tr>
												<td><s:property value="#subSchedule.assignDate" /></td>
												<td><s:property value="#subSchedule.period" /></td>
												<td><s:property value="#subSchedule.username" /></td>
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

			<!-- Modal phân lịch -->
			<div class="modal fade" id="mdModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<form action="newSubadminAssign" id="newSubadminAssignForm">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="defaultModalLabel">Phân công</h4>
							</div>
							<div class="modal-body">
								<div class="row clearfix">
									<div class="col-sm-6">
										<label>Subadmin</label>
										<div class="form-group">
											<select class="form-control show-tick" id="subadmin" data-live-search="true" name="subSchedule.userId">
												<option value=""> Chọn subadmin </option>
												<s:iterator value="subadmins" var="subadmin">
										        	<option value="<s:property value='#subadmin.userId' />"><s:property value='#subadmin.username' /></option>
										        </s:iterator>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<label>Phiên</label>
										<div class="form-group">
											<select class="form-control show-tick" id="period" data-live-search="true" name="subSchedule.period">
												<option value=""> Phiên</option>
										        <option value="Sáng">Sáng</option>
										        <option value="Chiều">Chiều</option>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<label>Ngày phân công</label>
			                            <div class="form-group">
			                            	<div class="form-line" id="bs_datepicker_container">
												<input type="text" class="form-control" id="assignDate"
													name="subSchedule.assignDateStr"
													data-date-format="dd/mm/yyyy" placeholder="Chọn ngày ...">
											</div>
			                             </div>
			                        </div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-link waves-effect">Xác nhận</button>
								<button type="button" class="btn btn-link waves-effect" data-dismiss="modal">Đóng</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- #END# Model phân lịch -->
			
		</div>
	</section>

	<%@ include file="components/template-scripts.jsp"%>
	
	<!-- Script check valid newSubadminAssign -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/subadminAssign/validSubadminAssign.js"></script>

</body>
</html>