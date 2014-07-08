<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div style="padding-top: 200px;">
	<div
		style="width: 50%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">

		<form action='<c:url value="/users"></c:url>' method="get">
			<fieldset>
				<label><h1>User Management:</h1></label>
				<table style="border-spacing: 20px; border-collapse: separate;">
					<tr>
						<td><label>Username: </label></td>
						<td><input type="text" name="username" value="" autocomplete="off"/></td>
					</tr>
					<tr>
						<td><label>Password: </label></td>
						<td><input type="password" name="password" value="" autocomplete="off"/></td>
					</tr>
					<tr>
						<td><button class="btn btn-primary" type="submit">Add</button></td>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
		
		<c:if test="${!empty userList}">
			<table class="table table-striped table-bordered">
				<th>#</th>
				<th>Username</th>
				<th>Action</th>
				
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${user.username}</td>
						<td><a class='btn btn-primary' href='<c:url value="/users/delete/${user.id}"></c:url>'>Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</div>
</div>