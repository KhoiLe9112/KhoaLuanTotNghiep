<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Sign In | ĐHKH Huế</title>
    <!-- Icon-->
    <link rel="icon" href="shared/images/logodhkh.png" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="shared/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="shared/plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="shared/plugins/animate-css/animate.css" rel="stylesheet" />

    <!-- Custom Css -->
    <link href="shared/css/style.css" rel="stylesheet">
    
</head>

<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a href="javascript:void(0);">LabTracker</a>
            <small>Quản lý phòng thực hành doanh nghiệp khoa CNTT trường ĐHKH Huế</small>
        </div>
        <div class="card">
            <div class="body">
            	<s:if test="hasActionMessage()">
            		<div class="success">
            			<s:actionmessage />
            		</div>
            	</s:if>
            	<s:if test="hasActionErrors()">
            		<div class="errors">
            			<s:actionerror />
            		</div>
            	</s:if>  
                <form action="confirmStudent" id="confirmStudentForm" method="POST">
                    <div class="msg">Xác nhận sinh viên</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" id="studentCode" name="studentCode" placeholder="Nhập mã sinh viên" autofocus>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 p-t-5">
                            <button class="btn btn-block bg-indigo waves-effect" type="submit">Xác nhận</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Jquery Core Js -->
    <script src="shared/plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="shared/plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="shared/plugins/node-waves/waves.js"></script>

    <!-- Validation Plugin Js -->
    <script src="shared/plugins/jquery-validation/jquery.validate.js"></script>

    <!-- Custom Js -->
    <script src="shared/js/admin.js"></script>
    <script src="shared/js/pages/examples/sign-in.js"></script>
    
    <!-- Script check valid student code -->
	<script type="text/javascript" charset="UTF-8" src="shared/js/confirmStudent.js"></script>
</body>

</html>