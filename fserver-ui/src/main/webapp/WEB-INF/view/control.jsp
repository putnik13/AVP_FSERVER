<%@page import="org.springframework.context.annotation.Import"%>
<%@page import="org.springframework.security.core.Authentication" %>
 <%@page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>FServer :: UI</title>
<!-- <link rel="stylesheet"
	href='<c:url value="/static/css/login/style.css"></c:url>'>-->

<link rel="stylesheet"
	href='<c:url value="/static/css/control/bootstrap.min.css"></c:url>'>
<script src='<c:url value="/static/js/control/jquery.min.js"></c:url>'></script>
<script
	src='<c:url value="/static/js/control/bootstrap.min.js"></c:url>'></script>
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
	<div>
		<nav id="myNavbar" class="navbar navbar-inverse navbar-fixed-top"
			role="navigation"> <!-- Brand and toggle get grouped for better mobile display -->
		<div class="container">
			<div style="color: #ccc" class="navbar-header">
				<img src='<c:url value="/static/images/logo.png"></c:url>'
					alt="logo" width="45px" />
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">Menu <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href='<c:url value="/menu"></c:url>'>Home</a></li>
							<li class="divider"></li>
							<li><a href='<c:url value="/control"></c:url>'>Recording
									Settings</a></li>
							<li><a href='<c:url value="/system"></c:url>'>System
									Settings</a></li>
						</ul></li>
				</ul>

				<%
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String userName = auth.getName();
				%>
				<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" data-toggle="dropdown"
							class="dropdown-toggle"><%= userName.toUpperCase() %><b class="caret"></b> </a>
							<ul class="dropdown-menu">
								<li><a href='<c:url value="/logout" />'>Logout</a></li>
							</ul>
						</li>
					</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		</nav>
	</div>

	<c:choose>
		<c:when test="${menuItem=='main'}">
			<jsp:include page="control_main_inc.jsp"></jsp:include>
		</c:when>
		<c:when test="${menuItem=='recording'}">
			<jsp:include page="control_record_inc.jsp"></jsp:include>
		</c:when>
		<c:when test="${menuItem=='system'}">
			<jsp:include page="control_system_inc.jsp"></jsp:include>
		</c:when>
	</c:choose>
	
	<label>${statusResponse}</label>
	
	<!-- Footer -->
	<div class="navbar-fixed-bottom row-fluid">
		<div class="navbar-inner">
			<div class="container">
				<!-- <hr> -->
				<footer>
				<p id="year" class="pull-left"
					style="margin-left: 20px; color: #aaa;"></p>
				<p class="pull-right" style="color: #aaa;">
					Powered by: <a href='mailto:admin@atanor.ru'
						style="color: #005baa; margin-right: 20px;">atanor.ru</a>
				</p>
				</footer>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var d = new Date();
		var n = d.getFullYear();
		document.getElementById("year").innerHTML = "&copy; 1993-"
				+ n
				+ " <a href='http://atanor.ru' target='_blank' style=\"color:#005baa;\">LLC \"Atanor\"</a>. All rights reserved.";
	</script>
</body>
</html>
