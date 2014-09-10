<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet"
	href='<c:url value="/static/css/control/bootstrap.min.css"></c:url>'>
<script src='<c:url value="/static/js/control/jquery.min.js"></c:url>'></script>
<script
	src='<c:url value="/static/js/control/bootstrap.min.js"></c:url>'></script>

<script type="text/javascript">
function refreshVideo() {
    var ifr = document.getElementsByName('video')[0];
    ifr.src = ifr.src;
}
</script>
	
<!-- <div style="padding-top: 100px;">
	<div
		style="width: 50%; margin: 0px auto; padding: 10px 10px;">-->
		<embed type="application/x-vlc-plugin" name="video" autoplay="yes"
			width="400" height="300" target="${streamUrl}" pluginspage="http://www.videolan.org" />
		<button id="video-refresh" class="btn" onclick="refreshVideo();">refresh</button>
<!-- 		<br />

	</div>
</div>-->