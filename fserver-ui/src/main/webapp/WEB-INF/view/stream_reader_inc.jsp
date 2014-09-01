<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="padding-top: 100px;">
	<div
		style="width: 50%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">

		<embed type="application/x-vlc-plugin" name="video" autoplay="yes"
			loop="yes" width="700" height="600" target="${streamUrl}" />

		<br />

	</div>
</div>