<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>FServer :: UI</title>
	<link rel="stylesheet" href='<c:url value="/static/css/login/style.css"></c:url>'>
	<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
<center>
	<center style="margin-top: 5%;">
		<img src='<c:url value="/static/images/logo.png"/>' alt="logo"/>
	</center>
	
	<c:forEach items="${userList}" var="user">
		<label>${user}</label>	
	</c:forEach>
	
	<table style="">
		<tr><td>
			<span>
			  <form method="post" class="login"> 
				    <p>
				      <label for="login">Login:</label>
				      <input type="text" name="username" id="login">
				    </p>
				
				    <p>
				      <label for="password">Password:</label>
				      <input type="password" name="password" id="password">
				    </p>
				
				    <p class="login-submit">
				      <button type="submit" class="login-button">Login</button>
				    </p>
			  </form>
			  </span>
		  </td></tr>
	</table>
</center>
<center>
</center>
		<footer>
			<!-- <p id="year" class="pull-left"></p>-->
			<!-- <p class="pull-right">Powered by: <a href='mailto:incu6us@ya.ru'>incu6us</a></p>-->
		</footer>
		
		<script type="text/javascript">
			var d = new Date();
			var n = d.getFullYear(); 
			document.getElementById("year").innerHTML = "&copy; 2004-"+n+" <a href='http://www.atanor.com/' target='_blank'>\"Atanor\"</a>. All rights reserved.";
		</script>
</body>
</html>
