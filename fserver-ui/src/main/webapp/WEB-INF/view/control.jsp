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
					alt="logo" width="60px" /> FServer User Interface
			</div>
		</div>
		</nav>
	</div>

	<div style="padding-top: 200px;">
		<div
			style="width: 50%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">
			<fieldset>
				<label>Menu:</label>
				<table style="border-spacing: 20px; border-collapse: separate;">
					<tr>
						<td><c:url value="/control?addChapter" var="addChapterButton" />
							<button class="btn btn-primary"
								onclick="location.href='<c:out value="${addChapterButton}"></c:out>'">Add
								Chapter</button></td>
						<td></td>
					</tr>
					<tr>
						<td><c:url value="/control?start" var="startButton" />
							<button class="btn btn-primary"
								onclick="location.href='<c:out value="${startButton}"></c:out>'">Start
								Recording</button></td>
						<td><c:url value="/control?stop" var="stopButton" />
							<button class="btn btn-primary"
								onclick="location.href='<c:out value="${stopButton}"></c:out>'">Stop
								Recording</button></td>
						<td><c:url value="/control?startRedirect"
								var="startRedirectButton" />
							<button class="btn btn-primary"
								onclick="location.href='<c:out value="${startRedirectButton}"></c:out>'">Start
								Redirect</button></td>
						<td><c:url value="/control?stopRedirect"
								var="stopRedirectButton" />
							<button class="btn btn-primary"
								onclick="location.href='<c:out value="${stopRedirectButton}"></c:out>'">Stop
								Redirect</button></td>
					</tr>
				</table>
			</fieldset>
		</div>

	</div>

	<!-- Footer -->
	<div class="navbar-fixed-bottom row-fluid">
		<div class="navbar-inner">
			<div class="container">
				<hr>
				<footer>
				<p id="year" class="pull-left"
					style="margin-left: 20px; color: #aaa;"></p>
				<p class="pull-right" style="color: #aaa;">
					Powered by: <a href='mailto:admin@atanor.ru'
						style="color: #01A9DB; margin-right: 20px;">atanor.ru</a>
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
				+ " <a href='http://atanor.ru' target='_blank' style=\"color:#01A9DB;\">LLC \"Atanor\"</a>. All rights reserved.";
	</script>
</body>
</html>
