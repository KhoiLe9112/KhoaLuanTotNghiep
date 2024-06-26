<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>LabTracker</title>
    
    <%@ include file="components/template-links.jsp" %>

	<link href="shared/css/reservations.css" rel="stylesheet" />

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
                <h2>
                    ĐẶT CHỖ
                </h2>
            </div>
            <!-- Content -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                ĐẶT CHỖ
                                <!-- <div class="button-demo js-modal-buttons" style="float: right;">
									<button type="button" data-color="indigo"
										class="btn bg-indigo waves-effect button-demo js-modal-buttons">Mã QR</button>
								</div> -->
                            </h2>
                        </div>
                        
                        <div class="body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active">
                                    <a href="#home_with_icon_title" data-toggle="tab">
                                        <i class="material-icons">home</i> Đặt ghế
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a href="#profile_with_icon_title" data-toggle="tab">
                                        <i class="material-icons">access_time</i> Ghế đã đặt
                                    </a>
                                </li>
                            </ul>
                            
                            <!-- Modal Mã QR -->
							<div class="modal fade" id="mdModal" tabindex="-1" role="dialog">
								<div class="modal-dialog" role="document">
									<div class="modal-content modal-col-indigo" id="showQRCode">
										<div class="modal-header">
											<h4 class="modal-title" id="defaultModalLabel">Mã QR</h4>
										</div>
										<div class="modal-body">
											<div class="row clearfix" style="text-align: center;">
												<img src="data:image/png;base64,<s:property value='%{qrCodeBase64}' />" alt="QR Code" />
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-link waves-effect" data-dismiss="modal">Đóng</button>
										</div>
									</div>
								</div>
							</div>
							<!-- #END# Model Mã QR -->

                            <!-- Tab panes -->
                            <div class="tab-content">
                            	
                            	<!-- Tab đặt ghế -->
                                <div role="tabpanel" class="tab-pane fade in active" id="home_with_icon_title">
                                    <form action="bookSeats" method="post" id="bookSeatsForm">
										<div class="row clearfix">
											<div class="col-sm-3">
												<label>Ngày sử dụng</label>
												<div class="form-group">
													<div class="form-line" id="bs_datepicker_container">
														<input type="text" class="form-control" id="usingDateStr"
															name="schedule.usingDateStr" data-date-format="dd/mm/yyyy"
															placeholder="Chọn ngày sử dụng...">
													</div>
												</div>
											</div>
											<div class="col-sm-3">
												<label>Buổi sử dụng</label>
												<div class="form-group">
													<div class="form-group">
														<select class="form-control show-tick" id="period"
															data-live-search="true" name="schedule.period">
															<option value="">-- Chọn buổi --</option>
															<option value="Sáng">Sáng</option>
															<option value="Chiều">Chiều</option>
														</select>
													</div>
												</div>
											</div>
											<%
					                        if (loggedInUser != null) {
					                        	int userId = loggedInUser.getUserId();
					                        	String role = loggedInUser.getRole();
					                        	if (Constant.ROLE_USER.equals(role)) {
					                        %>
					                        <input type="hidden" id="user" name="users.userId" value="<%=userId %>"/>
					                        <%	
					                        	} else {
					                        %>
					                        <div class="col-sm-4">
												<label>Sinh viên</label>
												<div class="form-group">
													<select class="form-control show-tick" id="user"
														data-live-search="true" name="users.userId">
														<option value="">-- Chọn sinh viên --</option>
														<s:iterator value="users" var="user">
															<option value="<s:property value='#user.userId' />">
																<s:property value='#user.username' /> -
																<s:property value="#user.email.substring(0, #user.email.indexOf('@')).toUpperCase()" />
															</option>
														</s:iterator>
													</select>
												</div>
											</div>
					                        <%
					                        		
					                        	}
					                        }
						            		%>
											
											<div class="col-sm-2" style="text-align: center">
												<button type="submit" id="btnSubmit" class="btn bg-indigo waves-effect">Xác nhận</button>
												<button type="button" id="btnRefresh" class="btn bg-indigo waves-effect">Refresh</button>
											</div>
			
										</div>
										<div class="row clearfix">
											<ul class="showcase">
												<li>
													<div class="seat"></div> <small>Còn trống</small>
												</li>
												<li>
													<div class="seat selected"></div> <small>Đang chọn</small>
												</li>
												<li>
													<div class="seat occupied"></div> <small>Đã được đặt</small>
												</li>
											</ul>
										</div>
										<div class="row clearfix">
											<div class="col-md-6" style="text-align: center;">
												<div class="col-md-3"></div>
												<div class="col-md-1">
													<div class="container" style="width: 25px; padding-top: 50px">
														<!-- ; display: flex; align-items: center; justify-content: center; -->
														<div id="1" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A1</div>
														<div id="2" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15PX">A2</div>
														<div id="3" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A3</div>
														<div id="4" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A4</div>
														<div id="5" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A5</div>
														<div id="6" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A6</div>
														<div id="7" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A7</div>
														<div id="19" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A19</div>
														<div id="20" class="seat"
															style="border-bottom-right-radius: 10px; border-top-right-radius: 10px; margin-top: 15px; margin-bottom: 15px">A20</div>
													</div>
												</div>
												<div class="col-md-4">
													<div class="container">
														<div class="row">
															<div id="8" class="seat"
																style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">A8</div>
															<div id="9" class="seat"
																style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">A9</div>
														</div>
														<div class="row">
															<div class="table2"></div>
														</div>
														<div class="row">
															<div id="10" class="seat"
																style="border-top-left-radius: 10px; border-top-right-radius: 10px;">A10</div>
															<div id="11" class="seat"
																style="border-top-left-radius: 10px; border-top-right-radius: 10px;">A11</div>
														</div>
													</div>
												</div>
												<div class="col-md-1">
													<div class="container" style="width: 25px; padding-top: 50px">
														<div id="12" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A12</div>
														<div id="13" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A13</div>
														<div id="14" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A14</div>
														<div id="15" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A15</div>
														<div id="16" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A16</div>
														<div id="17" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A17</div>
														<div id="18" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A18</div>
														<div id="21" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A21</div>
														<div id="22" class="seat"
															style="border-bottom-left-radius: 10px; border-top-left-radius: 10px; margin-top: 15px; margin-bottom: 15px">A22</div>
													</div>
												</div>
											</div>
			
											<div class="col-md-6">
												<div class="container">
													<div class="row">
														<div id="19" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">B1</div>
														<div id="20" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">B2</div>
														<div id="21" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">B3</div>
													</div>
													<div class="table"></div>
													<div class="row">
														<div id="22" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">B4</div>
														<div id="23" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">B5</div>
														<div id="24" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">B6</div>
													</div>
												</div>
			
												<div class="container">
													<div class="partition"></div>
												</div>
			
												<div class="container">
													<div class="row">
														<div id="25" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">C1</div>
														<div id="26" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">C2</div>
														<div id="27" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">C3</div>
													</div>
													<div class="table"></div>
													<div class="row">
														<div id="28" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">C4</div>
														<div id="29" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">C5</div>
														<div id="30" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">C6</div>
													</div>
												</div>
			
												<div class="container">
													<div class="partition"></div>
												</div>
			
												<div class="container">
													<div class="row">
														<div id="31" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">D1</div>
														<div id="32" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">D2</div>
														<div id="33" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">D3</div>
													</div>
													<div class="table"></div>
													<div class="row">
														<div id="34" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">D4</div>
														<div id="35" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">D5</div>
														<div id="36" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">D6</div>
													</div>
												</div>
												
												<div class="container">
													<div class="partition"></div>
												</div>
			
												<div class="container">
													<div class="row">
														<div id="37" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">E1</div>
														<div id="38" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">E2</div>
														<div id="39" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">E3</div>
													</div>
													<div class="table"></div>
													<div class="row">
														<div id="40" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">E4</div>
														<div id="41" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">E5</div>
														<div id="42" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">E6</div>
													</div>
												</div>
												
												<div class="container">
													<div class="partition"></div>
												</div>
			
												<div class="container">
													<div class="row">
														<div id="43" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">F1</div>
														<div id="44" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">F2</div>
														<div id="45" class="seat"
															style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">F3</div>
													</div>
													<div class="table"></div>
													<div class="row">
														<div id="46" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">F4</div>
														<div id="47" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">F5</div>
														<div id="48" class="seat"
															style="border-top-left-radius: 10px; border-top-right-radius: 10px;">F6</div>
													</div>
												</div>
											</div>
										</div>
									</form>
                                </div>
                                <!-- #END# Tab đặt ghế -->
                                
                                <!-- Tab lịch đã đặt -->
								<div role="tabpanel" class="tab-pane fade" id="profile_with_icon_title">
									<div class="row clearfix">
										<%
								        if (loggedInUser != null) {
								            int userId = loggedInUser.getUserId();
								        %>
								        <input type="hidden" id="userId" name="userId" value="<%=userId%>"/>
								        <%
								        }
								        %>
										<div class="col-sm-5">
											<label>Ngày sử dụng</label>
											<div class="form-group">
												<div class="form-line" id="bs_datepicker_container">
													<input type="text" class="form-control" id="usingDateStr2"
														name="schedule.usingDateStr" data-date-format="dd/mm/yyyy"
														placeholder="Chọn ngày sử dụng...">
												</div>
											</div>
										</div>
										<div class="col-sm-5">
											<label>Buổi sử dụng</label>
											<div class="form-group">
												<div class="form-group">
													<select class="form-control show-tick" id="period2"
														data-live-search="true" name="schedule.period">
														<option value="">-- Chọn buổi --</option>
														<option value="Sáng">Sáng</option>
														<option value="Chiều">Chiều</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-2" style="text-align: center">
											<button type="button" id="btnRefresh2" class="btn bg-indigo waves-effect">Refresh</button>
										</div>
									</div>
									<div class="table-responsive">
										<table id="dataTable" class="table table-bordered table-striped table-hover js-basic-example dataTable">
											<thead>
												<tr>
													<th>Họ tên</th>
													<th>Email</th>
													<th>Ghế</th>
													<th>Xác nhận</th>
													<th></th>
												</tr>
											</thead>
											<tbody id="scheduleTableBody">
											</tbody>
										</table>
									</div>
								</div>
								<!-- #END# Tab Lịch đã đặt -->
								
							</div>
                        </div>
					</div>
                </div>
            </div>
            <!-- #END# Content -->            
        </div>
    </section>

    <%@ include file="components/template-scripts.jsp" %>
    
    <!-- Script check valid seats -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/seatManage/validSeats.js"></script>
  
</body>

</html>
