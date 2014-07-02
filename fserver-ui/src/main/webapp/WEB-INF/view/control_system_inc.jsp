<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="padding-top: 100px;">
	<div
		style="width: 50%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">
		<form action='<c:url value="/system"></c:url>' method="get">
			<fieldset>
				<label><h1>Network Settings:</h1></label>
				<table style="border-spacing: 20px; border-collapse: separate;">
					<tr>
						<td><label>IP Address: </label></td>
						<td><input type="text" name="ip" value="${ip}" /></td>
					</tr>
					<tr>
						<td><label>Netmask: </label></td>
						<td><input type="text" name="netmask" value="${netmask}" /></td>
					</tr>
					<tr>
						<td><label>Gateway: </label></td>
						<td><input type="text" name="gateway" value="${gateway}" /></td>
					</tr>
					<tr>
						<td><button class="btn btn-primary" type="submit">Apply
								Changes</button></td>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>