<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href='<c:url value="/static/logo/favicon.png"/>'/>
    <title>LoveAndCare</title>
    <link href='<c:url value="/static/frontend_library/bootstrap/bootstrap-5/bootstrap.min.css"/>' rel="stylesheet"/>
    <link href='<c:url value="/static/frontend_library/fontawesome/css/all.min.css"/>' rel="stylesheet" type="text/css">
    <link href='<c:url value="/static/client/css/homePage.css"/>' rel="stylesheet" type="text/css">
</head>
<body id="page-top">
	
	<script type="text/javascript" src='<c:url value="/static/frontend_library/jquery/jquery.min.js"/>'></script>
	
	<tiles:insertAttribute name="homeHeader"/>
	
	<tiles:insertAttribute name="homeBanner"/>
	
	<tiles:insertAttribute name="homeBody"/>
	
	<tiles:insertAttribute name="homeFooter"/>

	<script type="text/javascript" src='<c:url value="/static/frontend_library/bootstrap/bootstrap-5/bootstrap.bundle.min.js"/>'></script>
	
	<!-- Login with google library -->
	<script src='<c:url value="/static/client/js/search.js"/>'></script>
	
</body>
</html>