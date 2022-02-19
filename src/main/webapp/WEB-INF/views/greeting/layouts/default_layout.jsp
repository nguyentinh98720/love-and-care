<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="final-project-ccdn">
    <meta name="author" content="tinhvn">
    
    <title>LoveAndCare <c:out value="${requestScope.pageName}"/></title>

	<link rel="icon" type="image/x-icon" href='<c:url value="/static/logo/favicon.png"/>'/>
	
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	        rel="stylesheet">
	<link href='<c:url value="/static/frontend_library/fontawesome/css/all.min.css"/>' rel='stylesheet' type='text/css'/>
	<link href='<c:url value="/static/main/css/sb-admin-2.min.css"/>' rel='stylesheet' type='text/css'/>

	<link href='<c:url value="/static/client/css/css.css"/>' type="text/css" rel="stylesheet"/>
	
</head>

<body>
 	<!-- JQuery -->    
    <script type="text/javascript" src='<c:url value="/static/frontend_library/jquery/jquery.min.js"/>'></script>

    <div class="container">
    
	    <tiles:insertAttribute name="page" />
    
    </div>
    
    <!-- Bootstrap -->
    <script type="text/javascript" src='<c:url value="/static/frontend_library/bootstrap/javaScript/bootstrap.bundle.min.js"/>'></script>
    <!-- JQuery easing -->
    <script type="text/javascript" src='<c:url value="/static/frontend_library/jquery_easing/jquery.easing.min.js"/>'></script>
    <!-- Main js -->
    <script type="text/javascript" src='<c:url value="/static/main/javaScript/sb-admin-2.min.js"/>'></script>
    
	<!-- Login with google library -->
	<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
	
	<!-- Login with facebook -->
	<div id="fb-root"></div>
    <script async defer crossorigin="anonymous" src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v12.0&appId=4821949414560775&autoLogAppEvents=1" nonce="txE4NASA"></script>
 
</body>
</html>