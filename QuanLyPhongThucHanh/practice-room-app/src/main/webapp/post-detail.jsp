<%@ page import="com.dhkh.model.User"%>
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
                <h2>
                    THÔNG BÁO
                </h2>
            </div>
            <!-- Content -->
            <div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<div class="header">
							<h2>${post.title}</h2>
							<small>Thời gian tạo: ${post.postTimeStr}</small>
						</div>

						<div class="body">
							<p class="lead">Nội dung:</p>
							<p>${post.content}</p>

						</div>

					</div>
				</div>
			</div>
            <!-- #END# Content -->            
        </div>
    </section>

    <%@ include file="components/template-scripts.jsp" %>
  
</body>

</html>
