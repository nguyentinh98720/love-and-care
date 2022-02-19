<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="final-project-ccdn">
<meta name="author" content="tinhnv">
<title>Admin Home</title>
<link rel="icon" type="image/x-icon" href='<c:url value="/static/logo/favicon.png"/>'/>

<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
<link href='<c:url value="/static/frontend_library/fontawesome/css/all.min.css"/>' rel='stylesheet' type='text/css'/>
<link href='<c:url value="/static/main/css/sb-admin-2.min.css"/>' rel='stylesheet' type='text/css'/>

<tiles:insertAttribute name="static_resource_css"/>

</head>
<body id="page-top">
	<!-- Page Wrapper -->
	<div id="wrapper">
	
		<tiles:insertAttribute name="menu" />
		
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
		
			<!-- Main content -->
			<div id="content">
			
				<tiles:insertAttribute name="header" />
				
				<tiles:insertAttribute name="body" />
				
			</div>
			
			<tiles:insertAttribute name="footer" />
			
		</div>
	
	</div>

	<tiles:insertAttribute name="logout"/>

	<!-- JQuery -->    
    <script type="text/javascript" src='<c:url value="/static/frontend_library/jquery/jquery.min.js"/>'></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src='<c:url value="/static/frontend_library/bootstrap/javaScript/bootstrap.bundle.min.js"/>'></script>
    <!-- JQuery easing -->
    <script type="text/javascript" src='<c:url value="/static/frontend_library/jquery_easing/jquery.easing.min.js"/>'></script>
    <!-- Main js -->
    <script type="text/javascript" src='<c:url value="/static/main/javaScript/sb-admin-2.min.js"/>'></script>
    
	<tiles:insertAttribute name="static_resource_js"/>
	
	<tiles:insertAttribute name="alert_box"/>
	
</body>
</html>