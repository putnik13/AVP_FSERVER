<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="padding-top: 200px;">
	<div
		style="width: 50%; margin: 0px auto; border: 1px solid #a1a1a1; padding: 10px 10px; background: #fff; border-radius: 25px;">

		<form action='<c:url value="/fconfig"></c:url>' method="post">
			<fieldset>
				<label><h1>System Settings:</h1></label>
				<table class="table table-striped">
					<tr>
						<td><label>Disk Space Alarm (Mb): </label></td>
						<td><input type="text" name="disk_space_alarm_mb" style="width:400px;" value="${config.getDisk_space_alarm_mb()}"/></td>
					</tr>
					<tr>
						<td><label>Disk Space Monitor Interval (ms): </label></td>
						<td><input type="text" name="disk_space_monitor_interval_ms" style="width:400px;" value="${config.getDisk_space_monitor_interval_ms()}"/></td>
					</tr>
					<tr>
						<td><label>Disk Space Threshold (Mb): </label></td>
						<td><input type="text" name="disk_space_threshold_mb" style="width:400px;" value="${config.getDisk_space_threshold_mb()}"/></td>
					</tr>
					<tr>
						<td><label>Media Container: </label></td>
						<td><input type="text" name="media_container" style="width:400px;" value="${config.getMedia_container()}"/></td>
					</tr>
					<tr>
						<td><label>Media Cut: </label></td>
						<td><input type="text" name="media_cut" style="width:400px;" value="${config.getMedia_cut()}"/></td>
					</tr>
					<tr>
						<td><label>Media Record: </label></td>
						<td><input type="text" name="media_record" style="width:400px;" value="${config.getMedia_record()}"/></td>
					</tr>
					<tr>
						<td><label>Media Record And Redirect: </label></td>
						<td><input type="text" name="media_record_and_redirect" style="width:400px;" value="${config.getMedia_record_and_redirect()}"/></td>
					</tr>
					<tr>
						<td><label>Media Redirect: </label></td>
						<td><input type="text" name="media_redirect" style="width:400px;" value="${config.getMedia_redirect()}"/></td>
					</tr>
					<tr>
						<td><label>Media Source: </label></td>
						<td><input type="text" name="media_source" style="width:400px;" value="${config.getMedia_source()}"/></td>
					</tr>
					<tr>
						<td><label>Recording Size Monitor Interval (ms): </label></td>
						<td><input type="text" name="recording_size_monitor_interval_ms" style="width:400px;" value="${config.getRecording_size_monitor_interval_ms()}"/></td>
					</tr>
					<tr>
						<td><label>Recording Size Monitor Start Delay (ms): </label></td>
						<td><input type="text" name="recording_size_monitor_start_delay_ms" style="width:400px;" value="${config.getRecording_size_monitor_start_delay_ms()}"/></td>
					</tr>
					<tr>
						<td><label>Recording Size Warn Attempts: </label></td>
						<td><input type="text" name="recording_size_warn_attempts" style="width:400px;" value="${config.getRecording_size_warn_attempts()}"/></td>
					</tr>
					<tr>
						<td><label>Recordings Output: </label></td>
						<td><input type="text" name="recordings_output" style="width:400px;" value="${config.getRecordings_output()}"/></td>
					</tr>
					<tr>
						<td><label>Redirect Url: </label></td>
						<td><input type="text" name="redirect_url" style="width:400px;" value="${config.getRedirect_url()}"/></td>
					</tr>
					<tr>
						<td><label>Socket API Port: </label></td>
						<td><input type="text" name="socket_api_port" style="width:400px;" value="${config.getSocket_api_port()}"/></td>
					</tr>
					<tr>
						<td></td>
						<td><button class="btn btn-primary" type="submit">Change Settings</button>&nbsp<a href='<c:url value="/fconfig/defaults"></c:url>' class="btn btn-primary">Default Settings</a></td>
					</tr>
				</table>
			</fieldset>
		</form>

	</div>
</div>