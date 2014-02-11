package com.atanor.fserver.facades;

import java.util.Date;
import java.util.List;

public class RecordingProcessInfo {

	private Date startTime;
	private Date endTime;
	private List<Date> tags;
	private String recordingPath;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public List<Date> getTags() {
		return tags;
	}

	public void setTags(final List<Date> tags) {
		this.tags = tags;
	}

	public boolean hasChapterTags() {
		return tags != null && tags.size() > 0;
	}

	public String getRecordingPath() {
		return recordingPath;
	}

	public void setRecordingPath(final String recordingPath) {
		this.recordingPath = recordingPath;
	}

}
