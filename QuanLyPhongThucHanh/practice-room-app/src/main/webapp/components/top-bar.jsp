<%@page import="com.dhkh.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="navbar">
	<div class="container-fluid">
		<div class="navbar-header">
			<a href="javascript:void(0);" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar-collapse"
				aria-expanded="false"></a> <a href="javascript:void(0);"
				class="bars"></a> <a class="navbar-brand" href="<s:url action='welcome' />">Lab Tracker</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<!-- Notifications -->
				
				<li class="dropdown"><a href="javascript:void(0);"
					class="dropdown-toggle" data-toggle="dropdown" role="button" id="btnNotice"> <i
						class="material-icons">notifications</i>
				</a>
					<ul class="dropdown-menu">
						<li class="header">THÔNG BÁO</li>
						<li class="body">
							<ul class="menu" id="notice-menu">
							</ul>
						</li>
						<li class="footer"><a href="javascript:void(0);">Hiển thị tất cả thông báo</a></li>
					</ul></li>
				<!-- #END# Notifications -->
			</ul>
		</div>
	</div>
</nav>