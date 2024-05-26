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
    
    <link rel="stylesheet" href="shared/css/font-awesome-4.7.0/css/font-awesome.min.css">
    <style>
	.google-login-button {
	  display: inline-block;
	  padding: 8px 16px;
	  border: none;
	  border-radius: 3px;
	  background-color: #4285F4;
	  color: #fff;
	  font-family: Roboto, sans-serif;
	  text-align: center;
	  cursor: pointer;
	  transition: background-color .3s;
	}
    </style>
    
</head>

<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a href="javascript:void(0);">LabTracker</a>
            <small>Quản lý phòng thực hành doanh nghiệp khoa CNTT trường ĐHKH Huế</small>
        </div>
        <div class="card">
            <div class="body">
            	<s:if test="hasActionErrors()">
            		<div class="errors">
            			<s:actionerror />
            		</div>
            	</s:if>  
                <form action="login" method="POST">
                    <div class="msg">Đăng nhập để sử dụng hệ thống</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" id="email" name="user.email" placeholder="Email" autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" id="password" name="user.password" placeholder="Mật khẩu">
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <input type="checkbox" name="rememberme" id="rememberme" class="filled-in chk-col-indigo">
                            <label for="rememberme">Lưu mật khẩu</label>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-indigo waves-effect" type="submit">ĐĂNG NHẬP</button>
                        </div>
                    </div>
                    <div class="msg">Đăng nhập bằng cách khác:</div>
                    <div class="row">
                        <div class="col-xs-12">
							<button class="btn btn-block google-login-button" id="btnGoogle" type="button">
								<i class="fa fa-google" style="padding-right: 10px"></i>Google
							</button>
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
    
    <!-- Firebase Google login Js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://www.gstatic.com/firebasejs/7.20.0/firebase.js"></script>
    
    <!-- Remember password Js -->
    <script src="shared/js/login/rememberPass.js"></script>
    
    <!-- Login by google Js -->
    <script src="shared/js/login/googleLogin.js"></script>
</body>

</html>