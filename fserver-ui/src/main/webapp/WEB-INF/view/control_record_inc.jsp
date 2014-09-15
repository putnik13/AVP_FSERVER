<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div style="padding-top: 100px;">
	<div
		style="width: 70%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">
		<fieldset>
			<label><h1>Recording:</h1></label>
			<table style="border-spacing: 20px; border-collapse: separate;">
				<tr>
					<td valign="top"><label>Menu:</label>
						<table style="border-spacing: 20px; border-collapse: separate;">
							<tr>
								<td><label>Add Chapter:</label></td>
								<td><c:url value="/control?addChapter"
										var="addChapterButton" />
									<button style="width: 100px;" class="btn btn-primary"
										onclick="location.href='<c:out value="${addChapterButton}"></c:out>'">Add
									</button></td>
								<td></td>
							</tr>
							<tr>
								<td><label>Start Recording:</label></td>
								<td><c:url value="/control?start" var="startButton" />
									<button style="width: 100px;" class="btn btn-primary"
										onclick="location.href='<c:out value="${startButton}"></c:out>'">Start
									</button></td>
							</tr>
							<tr>
								<td><label>Stop Recording:</label></td>
								<td><c:url value="/control?stop" var="stopButton" />
									<button style="width: 100px;" class="btn btn-primary"
										onclick="location.href='<c:out value="${stopButton}"></c:out>'">Stop
									</button></td>
							</tr>
							<tr>
								<td><label>Start Redirect:</label></td>
								<td><c:url value="/control?startRedirect"
										var="startRedirectButton" />
									<button style="width: 100px;" class="btn btn-primary"
										onclick="location.href='<c:out value="${startRedirectButton}"></c:out>'">Start
									</button></td>
							</tr>
							<tr>
								<td><label>Stop Redirect:</label></td>
								<td><c:url value="/control?stopRedirect"
										var="stopRedirectButton" />
									<button style="width: 100px;" class="btn btn-primary"
										onclick="location.href='<c:out value="${stopRedirectButton}"></c:out>'">Stop
									</button></td>
							</tr>
						</table></td>
					<td valign="top"><label>Status:</label>
						<!-- <table style="border-spacing: 20px; border-collapse: separate;">-->
						<table>
							<tr>
								<td>
									<div id="label-status"
										style="width: 400px; border: 1px solid #ccc; padding: 10px 10px; background: #ccc;">
										<c:choose>
											<c:when test="${recordStatus==''}">
												<label id="status-text" style="color: green;">READY</label>
											</c:when>
											<c:when test="${recordStatus!=''}">
												<label id="status-text" style="color: red;">${recordStatus}</label>
											</c:when>
										</c:choose>
									</div>
								</td>
							</tr>
							<tr>	
								<td>
									<embed type="application/x-vlc-plugin" name="video" autoplay="yes" 
										width="400" height="300" target="${streamUrl}" pluginspage="http://www.videolan.org" />
								</td>
							</tr>
							<tr>
								<td>
									<button id="video-refresh" class="btn" onclick="refreshVideo();">refresh</button>
								</td>
							</tr>
						</table></td>
				</tr>
			</table>
		</fieldset>
	</div>

</div>