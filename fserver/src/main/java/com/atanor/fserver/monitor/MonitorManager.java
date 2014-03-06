package com.atanor.fserver.monitor;

import javax.inject.Inject;
import javax.inject.Named;

public class MonitorManager {

	@Named("DiskSpace")
	@Inject
	private Monitor diskSpace;

	@Named("RecordingSize")
	@Inject
	private Monitor recordingSize;

	public void startMonitoring(final String recordingPath) {
		((MonitorRecordingSize) recordingSize).setRecordingPath(recordingPath);

		diskSpace.monitor();
		recordingSize.monitor();
	}

	public void stopMonitoring() {
		if (diskSpace != null) {
			diskSpace.interrupt();
		}
		if (recordingSize != null) {
			recordingSize.interrupt();
		}
	}
}
