<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
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
                <h2>THỐNG KÊ</h2>
            </div>
            
            <!-- Widgets -->
            <div class="row clearfix">
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    <div class="info-box bg-indigo hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">playlist_add_check</i>
                        </div>
                        <div class="content">
                            <div class="text">TỔNG SỐ LƯỢT ĐẶT GHẾ</div>
                            <div class="number"><s:property value="totalReservations" /></div>
                        </div>
                    </div>
                </div>
                <s:iterator value="periodStatistics" var="periodStatistic" status="counter">
                	<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
	                    <div class="info-box bg-indigo hover-expand-effect">
	                        <div class="icon">
	                            <i class="material-icons">help</i>
	                        </div>
	                        <div class="content">
	                            <div class="text">LƯỢT ĐẶT GHẾ BUỔI ${periodStatistic.period}</div>
	                            <div class="number">${periodStatistic.total}</div>
	                        </div>
	                    </div>
	                </div>
                </s:iterator>
            </div>
            <!-- #END# Widgets -->
            
            <!-- Visitors -->
            <%-- <div class="row clearfix">
                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                    <div class="card">
                        <div class="body bg-pink">
                            <div class="sparkline" data-type="line" data-spot-Radius="4" data-highlight-Spot-Color="rgb(233, 30, 99)" data-highlight-Line-Color="#fff"
                                 data-min-Spot-Color="rgb(255,255,255)" data-max-Spot-Color="rgb(255,255,255)" data-spot-Color="rgb(255,255,255)"
                                 data-offset="90" data-width="100" data-height="100" data-line-Width="2" data-line-Color="rgba(255,255,255,0.7)"
                                 data-fill-Color="rgba(0, 188, 212, 0)">
                                12,10,9,6,5,6,10,5,7,5,12,13,7,12,11
                            </div>
                            <ul class="dashboard-stat-list">
                                <li>
                                    TODAY
                                    <span class="pull-right"><b>1 200</b> <small>USERS</small></span>
                                </li>
                                <li>
                                    YESTERDAY
                                    <span class="pull-right"><b>3 872</b> <small>USERS</small></span>
                                </li>
                                <li>
                                    LAST WEEK
                                    <span class="pull-right"><b>26 582</b> <small>USERS</small></span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> --%>
            <!-- #END# Visitors -->
            
            <!-- Top User -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>TOP SINH VIÊN ĐẶT GHẾ NHIỀU</h2>
                        </div>
                        
                        <div class="body">
							<div class="body table-responsive">
	                            <table class="table table-hover table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th>#</th>
	                                        <th>SINH VIÊN</th>
	                                        <th>SỐ LƯỢT ĐẶT GHẾ</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<s:iterator value="userStatistics" var="userStatistic" status="counter">
	                                		<tr>
		                                        <th scope="row">${counter.index + 1}</th>
		                                        <td>${userStatistic.username}</td>
		                                        <td>${userStatistic.total}</td>
		                                    </tr>
	                                	</s:iterator>
	                                </tbody>
	                            </table>
	                        </div>
                        </div>
                        
					</div>
                </div>
            </div>
            <!-- #END# Top User -->
            
            <!-- Top Seat -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>TOP GHẾ ĐƯỢC ĐẶT NHIỀU NHIỀU</h2>
                        </div>
                        
                        <div class="body">
							<div class="body table-responsive">
	                            <table class="table table-hover table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th>#</th>
	                                        <th>GHẾ</th>
	                                        <th>SỐ LƯỢT ĐẶT</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<s:iterator value="seatStatistics" var="seatStatistic" status="counter">
	                                		<tr>
		                                        <th scope="row">${counter.index + 1}</th>
		                                        <td>${seatStatistic.seatName}</td>
		                                        <td>${seatStatistic.total}</td>
		                                    </tr>
	                                	</s:iterator>
	                                </tbody>
	                            </table>
	                        </div>
                        </div>
                        
					</div>
                </div>
            </div>
            <!-- #END# Top Seat -->
            
        </div>
    </section>

    <%@ include file="components/template-scripts.jsp" %>
    
    <!-- Script check valid seats -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/seatManage/validSeats.js"></script>
  
</body>

</html>
